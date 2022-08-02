package com.lunar.lunarMgmt.common.utils;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.intf.AdminbaseIdNmSetUtilInterface;

import java.time.LocalDateTime;

public class AdminBaseIdNmSetUtilIImpl implements AdminbaseIdNmSetUtilInterface<AdminUserDto, AdminUserDto> {

  @Override
  public AdminUserDto adminBaseInfoSetting(AdminUserDto model, AdminUserDto currentUser) {
    model.setCreateId(currentUser.getAdminUserId());
    model.setCreateNm(currentUser.getAdminUserNm());
    model.setCreateDatetime(LocalDateTime.now());
    model.setModifyId(currentUser.getAdminUserId());
    model.setModifyNm(currentUser.getAdminUserNm());
    model.setModifyDatetime(LocalDateTime.now());
    return model;
  }
}
