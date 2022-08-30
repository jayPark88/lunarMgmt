package com.lunar.lunarMgmt.common.sercurity;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import com.lunar.lunarMgmt.common.exception.ForbiddenTokenException;
import com.lunar.lunarMgmt.common.exception.NotFoundTokenException;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import com.lunar.lunarMgmt.common.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final AdminAuthRepository authRepo;
  private final CookieUtil cookieUtil;

  // @Description : 권한검사 Skip할 RequestURI 목록
  private boolean isSkipped(String requestUri) {
    List<String> skipPathList = new ArrayList<>();
    skipPathList.add("/auth/login");
    skipPathList.add("/auth/logout");
    skipPathList.add("/auth/refresh");
    skipPathList.add("/swagger-");
    skipPathList.add("/v3");
    skipPathList.add("/setting/auth/list");
    skipPathList.add("/system/save/user");
    skipPathList.add(("/setting/code/groups"));


    for (int i = 0; i < skipPathList.size(); i++) {
      if (requestUri.contains(skipPathList.get(i))) {
        return true;
      }
    }

    return false;
  }

  // @Description : 권한검사가 필요한 RequestURI 등록
  private boolean isRequiredValidation(String requestUri) {
    return requestUri.equals("/admin/setting/menus/tree");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    if (isSkipped(request.getRequestURI())) {
      filterChain.doFilter(request, response);
    }else{
      String jwtToken = request.getHeader(JwtUtil.AUTHRIZATION_HEADER_NAME);
      if (jwtToken == null) {
        Cookie cookie = cookieUtil.getCookie(request, "auth._token.local");
        if (cookie != null)
          jwtToken = cookie.getValue();
      }

      AdminUserDto adminUserDto = new AdminUserDto();

      // accessToken 검증
      if (jwtToken != null) {
        try {
          adminUserDto = jwtUtil.getUserDtoInToken(jwtToken);
          // 추후 refreshToken으로 accessToken 재발급 시스템 개발 요함.
        } catch (ExpiredTokenException e) {
          throw new ExpiredTokenException();
        }
        setUsernameAuthentication(adminUserDto, request);
      } else
        throw new NotFoundTokenException();

      // 권한 검증이 필요한 API라면 검사
      String referer = request.getHeader(JwtUtil.REFERER_HEADER_NAME);

      if (ObjectUtils.isEmpty(adminUserDto.getAuthSeq()))
        throw new ForbiddenTokenException();

      if (isRequiredValidation(request.getRequestURI())
              && !validAuthoritiesWithReferer(referer, adminUserDto.getAuthSeq()))
        throw new ForbiddenTokenException();

      filterChain.doFilter(request, response);
    }
  }

  // @Description : 권한검사할 경우 요청한 Referer가 본인 권한에 등록되어 있는 메뉴인지 검사
  private boolean validAuthoritiesWithReferer(String referer, Long authSeq) {
    String refererPath = String.format("/%s", referer.replaceAll("(((http)|(https))://)(([^/]+)(:?)/?)", ""));

    log.debug("referer: {}", referer);
    log.debug("referer path: {}", refererPath);

    List<String> authUrlList = authRepo.selectMenuPageURListLByAuth(authSeq);

    authUrlList.add("/"); // 기본 root Path는 통과
    for (int i = 0; i < authUrlList.size(); i++) {
      if (isEqualsPath(authUrlList.get(i), refererPath))
        return true;
    }

    return false;
  }

  private boolean isEqualsPath(String authUrl, String refererPath) {
    String[] authUrlTokens = authUrl.split("/");
    String[] refererTokens = refererPath.split("/");

    if (authUrlTokens.length > refererTokens.length)
      return false;

    for(int i=1; i<authUrlTokens.length; i++) {
      if (!authUrlTokens[i].equals(refererTokens[i]))
        return false;
    }

    return true;
  }

  // @Description : @AuthenticationPrincipal UserDto userDto 로 주입받을 수 있다.
  private void setUsernameAuthentication(AdminUserDto adminUserDto, HttpServletRequest request) {
    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(adminUserDto, null, null);

    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(upat);
  }

}
