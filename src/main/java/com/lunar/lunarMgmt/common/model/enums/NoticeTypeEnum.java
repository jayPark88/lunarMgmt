package com.lunar.lunarMgmt.common.model.enums;

import com.lunar.lunarMgmt.common.intf.CommonCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NoticeTypeEnum implements CommonCodeEnum {

    ALL("all"),
    NOTICE("공지사항");


    @Getter
    private String codeName;

    @Override
    public String getCode() {
        return this.toString();
    }
}
