package com.lunar.lunarMgmt.api.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.login.model.LoginAdminUserDto;
import com.lunar.lunarMgmt.api.login.model.Tokens;
import com.lunar.lunarMgmt.api.login.service.LoginService;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import com.lunar.lunarMgmt.common.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/login")
  public Tokens login(@RequestBody LoginAdminUserDto user ) throws NotFoundUserException, JsonProcessingException {
    return loginService.login(user);
  }

  @PostMapping("/refresh")
  public Tokens refresh(@RequestBody Tokens tokens) throws ExpiredTokenException, JsonProcessingException {
    return loginService.refreshToken(tokens.getRefreshToken());
  }

  @GetMapping("/user")
  public AdminUserDto tokenUserInfo(@AuthenticationPrincipal AdminUserDto userDto) {
    return loginService.tokenUserInfo(userDto);
  }

}
