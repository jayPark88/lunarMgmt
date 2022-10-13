package com.lunar.lunarMgmt.common.jpa.listener;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminBaseEntity;
import com.lunar.lunarMgmt.common.utils.AdminAuthenticationUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AdminBaseEntityListener {
  @PrePersist
  void onSave(AdminBaseEntity entity) {
    AdminUserDto adminUserDto = AdminAuthenticationUtils.getAdminUserDto();
    if(!ObjectUtils.isEmpty(adminUserDto.getAdminUserId())){
      entity.setCreateId(adminUserDto.getAdminUserId());
      entity.setCreateNm(adminUserDto.getAdminUserNm());
      entity.setModifyId(adminUserDto.getAdminUserId());
      entity.setModifyNm(adminUserDto.getAdminUserNm());
    }
  }

  @PreUpdate
  void onUpdate(AdminBaseEntity entity) {
    AdminUserDto adminUserDto = AdminAuthenticationUtils.getAdminUserDto();

    if(!ObjectUtils.isEmpty(adminUserDto.getAdminUserId())){
      entity.setModifyId(adminUserDto.getAdminUserId());
      entity.setModifyNm(adminUserDto.getAdminUserNm());
    }
  }
}
