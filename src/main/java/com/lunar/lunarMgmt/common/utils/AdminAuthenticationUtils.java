package com.lunar.lunarMgmt.common.utils;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminAuthenticationUtils {
  // Admin User 가져오기
  public static AdminUserDto getAdminUserDto() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      return (AdminUserDto)authentication.getPrincipal();

    } catch (Exception e) {
      return new AdminUserDto();
    }
  }
}
