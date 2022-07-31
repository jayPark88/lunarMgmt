package com.lunar.lunarMgmt.api.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.login.model.Tokens;
import com.lunar.lunarMgmt.api.login.service.LoginService;
import com.lunar.lunarMgmt.common.exception.ExpiredTokenException;
import com.lunar.lunarMgmt.common.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/login")
  public Tokens login(@RequestBody AdminUserDto user, HttpServletResponse response ) throws NotFoundUserException, JsonProcessingException {
    return loginService.login(user, response);
  }

  @PostMapping("/refresh")
  public Tokens refresh(@RequestBody Tokens tokens) throws ExpiredTokenException, JsonMappingException, JsonProcessingException {
    return loginService.refreshToken(tokens.getRefreshToken());
  }

}
