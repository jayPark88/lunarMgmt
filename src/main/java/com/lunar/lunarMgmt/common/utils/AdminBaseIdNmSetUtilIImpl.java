package com.lunar.lunarMgmt.common.utils;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.intf.AdminbaseIdNmSetUtilInterface;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

public class AdminBaseIdNmSetUtilIImpl implements AdminbaseIdNmSetUtilInterface<AdminUserDto, AdminUserDto> {

  @Override
  public AdminUserDto adminBaseInfoSetting(AdminUserDto model, AdminUserDto currentUser) {
    model.setCreateId(ObjectUtils.isEmpty(currentUser)?"jayPark":currentUser.getAdminUserId());
    model.setCreateNm(ObjectUtils.isEmpty(currentUser)?"parkNm":currentUser.getAdminUserNm());
    model.setCreateDatetime(LocalDateTime.now());
    model.setModifyId(ObjectUtils.isEmpty(currentUser)?"jayPark":currentUser.getAdminUserId());
    model.setModifyNm(ObjectUtils.isEmpty(currentUser)?"parkNm":currentUser.getAdminUserNm());
    model.setModifyDatetime(LocalDateTime.now());
    return model;
  }
}
