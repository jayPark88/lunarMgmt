package com.lunar.lunarMgmt.api.setting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonCodeSort {

    private String groupCode;
    private String code;
    private int sortNum;
    private int beforeSortNum;

}
