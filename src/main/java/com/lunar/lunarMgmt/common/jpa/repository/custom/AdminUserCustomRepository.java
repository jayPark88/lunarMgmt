package com.lunar.lunarMgmt.common.jpa.repository.custom;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;

import java.util.List;

public interface AdminUserCustomRepository {
    List<AdminUserDto> findByAdminUserList();
}
