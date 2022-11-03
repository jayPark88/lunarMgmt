package com.lunar.lunarMgmt.api.community.board.model;

import com.lunar.lunarMgmt.common.model.enums.BoardTypeEnum;
import lombok.Getter;

@Getter
public class BoardSearchDto {
    private BoardTypeEnum boardTypeEnum;
    private String title;
    private String content;
}
