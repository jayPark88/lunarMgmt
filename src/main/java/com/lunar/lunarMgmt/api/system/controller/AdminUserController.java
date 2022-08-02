package com.lunar.lunarMgmt.api.system.controller;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
public class AdminUserController {
  private final AdminUserService adminUserService;

  @PostMapping("/save/user")
  public void saveAdminUser(@RequestBody AdminUserDto adminUserDto, @AuthenticationPrincipal AdminUserDto currentUser){
    adminUserService.saveAdminUser(adminUserDto, currentUser);
  }
}
