package com.lunar.lunarMgmt.api.system.controller;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.model.AdminUserSearchDto;
import com.lunar.lunarMgmt.api.system.service.AdminUserService;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
public class AdminUserController {
  private final AdminUserService adminUserService;

  @PostMapping("/save/user")
  public void saveAdminUser(@RequestBody AdminUserDto adminUserDto, @AuthenticationPrincipal AdminUserDto currentUser){
    adminUserService.saveAdminUser(adminUserDto, currentUser);
  }

  // 단순히 page 목록을
  @GetMapping("/get/user/list")
  public PageResponse<AdminUserEntity, AdminUserDto> searchAdminUserList(@ModelAttribute AdminUserSearchDto adminUserSearchDto
          , @ModelAttribute PageRequest pageRequest){
    return adminUserService.searchAdminUserList(adminUserSearchDto, pageRequest);
  }
}
