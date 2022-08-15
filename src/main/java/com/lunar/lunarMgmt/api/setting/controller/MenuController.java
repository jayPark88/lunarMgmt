package com.lunar.lunarMgmt.api.setting.controller;

import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/setting/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/list")
    public List<AdminMenuDto> selectMenuList() {
        return menuService.selectMenuList();
    }
}
