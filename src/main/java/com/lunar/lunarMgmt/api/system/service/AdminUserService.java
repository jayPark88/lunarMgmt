package com.lunar.lunarMgmt.api.system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.abst.sub.AdminUserSub;
import com.lunar.lunarMgmt.api.system.model.AdminUserListSearchDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.model.PageRequest;
import com.lunar.lunarMgmt.common.model.PageResponse;
import com.lunar.lunarMgmt.common.utils.AdminBaseIdNmSetUtilIImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserService {
  private final AdminUserSub adminUserSub;

  public void saveAdminUser(AdminUserDto adminUserDto, AdminUserDto currentUser){
    adminUserSub.setAdminbaseIdNmSetUtilInterface(new AdminBaseIdNmSetUtilIImpl());
    adminUserSub.saveAdminUser(new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(adminUserSub.adminbaseIdNmSetUtil.adminBaseInfoSetting(adminUserDto, currentUser), AdminUserDto.class));
  }

  public PageResponse<AdminUserEntity, AdminUserDto> searchAdminUserList(AdminUserListSearchDto searchDto, PageRequest pageRequest){
    pageRequest.setSort("adminUserSeq");
    pageRequest.setDirection("desc");
    return adminUserSub.searchAdminUserList(searchDto, pageRequest);
  }

  public boolean adminUserDuplicateCheck(String siteId){
    return adminUserSub.adminUserDuplicateCheck(siteId);
  }
}
