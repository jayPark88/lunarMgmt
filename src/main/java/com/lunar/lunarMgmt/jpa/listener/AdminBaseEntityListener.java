package com.lunar.lunarMgmt.jpa.listener;

import com.lunar.lunarMgmt.common.dto.UserDto;
import com.lunar.lunarMgmt.common.util.AdminAuthenticationUtils;
import com.lunar.lunarMgmt.jpa.entity.AdminBaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AdminBaseEntityListener {
    @PrePersist
    void onSave(AdminBaseEntity entity) {
        UserDto userDto = AdminAuthenticationUtils.getUserDto();

        entity.setCreateId(userDto.getUserId());
        entity.setCreateNm(userDto.getUserNm());

        entity.setModifyId(userDto.getUserId());
        entity.setModifyNm(userDto.getUserNm());
    }

    @PreUpdate
    void onUpdate(AdminBaseEntity entity) {
        UserDto userDto = AdminAuthenticationUtils.getUserDto();

        entity.setModifyId(userDto.getUserId());
        entity.setModifyNm(userDto.getUserNm());
    }
}
