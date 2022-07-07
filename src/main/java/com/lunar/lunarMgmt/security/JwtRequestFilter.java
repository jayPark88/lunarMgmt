package com.lunar.lunarMgmt.security;

import com.lunar.lunarMgmt.common.dto.UserDto;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import com.lunar.lunarMgmt.common.exception.ForbiddenTokenException;
import com.lunar.lunarMgmt.common.exception.NotFoundTokenException;
import com.lunar.lunarMgmt.common.util.CookieUtil;
import com.lunar.lunarMgmt.jpa.repository.AdminAuthRepository;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // URI가 skip대상이면 아래의 검증을 하지 않고 바로 통과 됨.
        if (isSkipped(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(JwtUtil.AUTHRIZATION_HEADER_NAME);
        if (jwtToken == null) {
            Cookie cookie = cookieUtil.getCookie(request, "auth._token.local");
            if (cookie != null)
                jwtToken = cookie.getValue();
        }

        UserDto userDto = null;

        // accessToken 검증
        if (jwtToken != null) {
            try {
                userDto = jwtUtil.getUserDtoInToken(jwtToken);
            } catch (ExpiredTokenException e) {
                throw new ExpiredTokenException();
            }
            setUsernameAuthentication(userDto, request);
        } else {
            throw new NotFoundTokenException();
        }

        // 권한 검증이 필요한 API라면 검사
        String referer = request.getHeader(JwtUtil.REFERER_HEADER_NAME);

        if (ObjectUtils.isEmpty(userDto.getAuthSeq()))
            throw new ForbiddenTokenException();

        if (isRequiredValidation(request.getRequestURI())
                && !validAuthoritiesWithReferer(referer, userDto.getAuthSeq()))
            throw new ForbiddenTokenException();

        filterChain.doFilter(request, response);
    }

    // 권한검사 Skip할 RequestURI 목록
    private boolean isSkipped(String requestUri) {
        List<String> skipPathList = new ArrayList<>();
        skipPathList.add("/auth/login");
        skipPathList.add("/auth/logout");
        skipPathList.add("/auth/join");

        for (int i = 0; i < skipPathList.size(); i++) {
            if (requestUri.contains(skipPathList.get(i))) {
                return true;
            }
        }
        return false;
    }

    // 권한검사가 필요한 RequestURI 등록
    // menu로 하는 이유는 메인 화면에 접속 시 항상 저 메뉴가 reloading이 되니
    private boolean isRequiredValidation(String requestUri) {
        return requestUri.equals("/admin/setting/menus/tree");
    }

    // @AuthenticationPrincipal UserDto userDto 로 주입받을 수 있다.
    private void setUsernameAuthentication(UserDto userDto, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken upat =
                new UsernamePasswordAuthenticationToken(userDto, null, null);

        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(upat);
    }

    // 권한검사할 경우 요청한 Referer가 본인 권한에 등록되어 있는 메뉴인지 검사
    // 여기서 부터 다시 시작
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


}
