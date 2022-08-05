package com.lunar.lunarMgmt.api.system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.system.abst.sub.AdminUserSub;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.utils.AdminBaseIdNmSetUtilIImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserService {

  private final AdminUserSub adminUserSub;

  // HQ-ADMIN 사용자 신규 등록 및 수정
  public void saveAdminUser(AdminUserDto adminUserDto, AdminUserDto currentUser){
    adminUserSub.setAdminbaseIdNmSetUtilInterface(new AdminBaseIdNmSetUtilIImpl());
    adminUserSub.saveAdminUser(new ObjectMapper().registerModule(new JavaTimeModule()).convertValue(adminUserSub.adminbaseIdNmSetUtil.adminBaseInfoSetting(adminUserDto, currentUser), AdminUserDto.class));
  }
}
