package com.lunar.lunarMgmt.api.setting.controller;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/tree")
    public List<AdminMenuDto> selectMenuTree(@AuthenticationPrincipal AdminUserDto adminUserDto) {
        List<AdminMenuDto> menuTree = menuService.selectMenuTree(adminUserDto);
        menuTree.removeIf((menuDto) -> menuDto.getPageUrl().equals("/")); // 메인화면 이 있으면 그건 NavigationMenu에서 제거
        return menuTree;
    }
}
