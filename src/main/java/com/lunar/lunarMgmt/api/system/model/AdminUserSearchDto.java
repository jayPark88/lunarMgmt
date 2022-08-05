package com.lunar.lunarMgmt.api.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserSearchDto {
    private String adminUserNm;
    private Long authSeq;
    private Character useYn;
}
