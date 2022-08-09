package com.lunar.lunarMgmt.common.jpa.repository.custom;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.model.AdminUserListSearchDto;
import com.lunar.lunarMgmt.common.model.PageRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminUserCustomRepository {
    List<AdminUserDto> findByAdminUserList(AdminUserListSearchDto adminUserListSearchDto);
    Page<AdminUserDto> findByAdminUserPage(AdminUserListSearchDto adminUserListSearchDto, PageRequest pageRequest);
}
