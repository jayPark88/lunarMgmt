package com.lunar.lunarMgmt.api.system.model;

import lombok.Data;

@Data
public class AdminUserListSearchDto {
    private String adminUserId;
    private String adminUserNm;
    private String dept;
    private String position;
    private Character useYn;
}
