package com.lunar.lunarMgmt.api.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.login.model.LoginAdminUserDto;
import com.lunar.lunarMgmt.api.login.model.Tokens;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import com.lunar.lunarMgmt.common.exception.NotFoundUserException;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import com.lunar.lunarMgmt.common.sercurity.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

  private final AdminUserRepository adminUserRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  public Tokens login(LoginAdminUserDto adminUser) throws NotFoundUserException, JsonProcessingException {
    Optional<AdminUserEntity> adminUserEntity = adminUserRepo.findByAdminUserId(adminUser.getAdminUserId());

    if (adminUserEntity.isEmpty()) {
      throw new NotFoundUserException();
    }else{
      if (adminUserEntity.get().getDeleteYn().equals('Y')) {
        throw new NotFoundUserException("삭제된 사용자입니다.");
      }

      if (adminUserEntity.get().getUseYn().equals('N')) {
        throw new NotFoundUserException("기관 또는 사용자가 이용 가능한 상태가 아닙니다.");
      }

      // 패스워드 체크 (필요 시 위치 이동)
      if (!passwordEncoder.matches(adminUser.getAdminUserPwd(), adminUserEntity.get().getAdminUserPwd())) {
        throw new NotFoundUserException();
      }

      AdminUserDto adminUserDto = new AdminUserDto(adminUserEntity.get()).withoutPasswd();

      // 패스워드 체크 후 비밀번호 null
      final String accessToken = jwtUtil.generateToken(adminUserDto);
      final String refreshToken = jwtUtil.generateRefreshToken(adminUserDto);

      return new Tokens(accessToken, refreshToken);
    }
  }

  public Tokens refreshToken(String refreshToken) throws ExpiredTokenException, JsonProcessingException {
    String newRefreshToken;

    if (Optional.ofNullable(refreshToken).isPresent()) {
      AdminUserDto adminUserDto = jwtUtil.getUserDtoInToken(refreshToken);
      newRefreshToken = jwtUtil.generateRefreshToken(adminUserDto);
    }else{
      throw new ExpiredTokenException();
    }
    // 기존에 refreshToken을 accessToken으로, newRefreshToken을 refreshToken으로 변경
    return new Tokens(refreshToken, newRefreshToken);
  }

}
