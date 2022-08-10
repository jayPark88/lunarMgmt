package com.lunar.lunarMgmt.api.system.controller;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.model.AdminUserListSearchDto;
import com.lunar.lunarMgmt.api.system.service.AdminUserService;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/system")
public class AdminUserController {
  private final AdminUserService adminUserService;

  @PostMapping("/save/user")
  public void saveAdminUser(@RequestBody AdminUserDto adminUserDto, @AuthenticationPrincipal AdminUserDto currentUser){
    adminUserService.saveAdminUser(adminUserDto, currentUser);
  }

  @GetMapping("/user/list")
  public PageResponse<AdminUserEntity, AdminUserDto> searchAdminUserList(@ModelAttribute AdminUserListSearchDto searchDto
          , @ModelAttribute PageRequest pageRequest){
    return adminUserService.searchAdminUserList(searchDto, pageRequest);
  }

  @GetMapping("/duplicate/user/{adminUserId}")
  public boolean adminUserDuplicateCheck(@PathVariable String adminUserId){
    return adminUserService.adminUserDuplicateCheck(adminUserId);
  }
}
