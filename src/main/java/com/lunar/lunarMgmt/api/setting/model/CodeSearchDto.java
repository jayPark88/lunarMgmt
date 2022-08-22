package com.lunar.lunarMgmt.api.setting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CodeSearchDto{
    private String groupCode;
    private String groupCodeName;
    private String code;
    private String codeName;
    private Character useYn;

    public GroupCodeSearchDto toGroupCodeSearchDto() {
        return GroupCodeSearchDto.builder()
                .groupCode(groupCode)
                .groupCodeName(groupCodeName)
                .useYn(useYn)
                .build();
    }

    public CommonCodeSearchDto toCommonCodeSearchDto(String groupCode) {
        return CommonCodeSearchDto.builder()
                .groupCode(groupCode)
                .code(code)
                .codeName(codeName)
                .useYn(useYn)
                .build();
    }
}
