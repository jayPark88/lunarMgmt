package com.lunar.lunarMgmt.api.setting.model;

import java.util.ArrayList;
import java.util.List;

import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import lombok.Data;

// Front 메뉴트리 화면단에서 사용할 Menu

@Data
public class VueMenuDto {

    private String title;
    private int level;
    private AdminMenuDto data;
    private List<VueMenuDto> children;

    public VueMenuDto() {
        children = new ArrayList<>();
    }

    public VueMenuDto(AdminMenuEntity entity) {
        this();
        data = new AdminMenuDto(entity);
        title = data.getMenuNm();
        level = data.getMenuLevel();

    }
}