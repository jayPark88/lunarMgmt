package com.lunar.lunarMgmt.api.setting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupCodeSearchDto {
    private String groupCode;
    private String groupCodeName;
    private Character useYn;
}
