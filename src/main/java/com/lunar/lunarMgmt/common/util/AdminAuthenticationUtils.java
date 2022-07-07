package com.lunar.lunarMgmt.common.util;

import com.lunar.lunarMgmt.common.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminAuthenticationUtils {
    public static UserDto getUserDto() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (UserDto)authentication.getPrincipal();

        } catch (Exception e) {
            return new UserDto();
        }
    }
}
