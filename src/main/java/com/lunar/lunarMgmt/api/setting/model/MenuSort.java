package com.lunar.lunarMgmt.api.setting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MenuSort {

    private Long parentMenuId;
    private Long menuSeq;
    private int sortNum;
    private int beforeSortNum;

}
