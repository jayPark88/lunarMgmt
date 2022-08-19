package com.lunar.lunarMgmt.api.setting.controller;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.MenuSort;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.api.setting.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/vuetree")
    public List<VueMenuDto> selectVueMenuTree() {
        return menuService.selectVueMenuTree();
    }

    @GetMapping(value = "/{menuSeq}")
    public AdminMenuDto selectMenu(@PathVariable Long menuSeq) throws Exception {
        return menuService.selectMenu(menuSeq);
    }

    @PostMapping
    public void saveMenu(@RequestBody AdminMenuDto adminMenuDto) {
        menuService.saveMenu(adminMenuDto);
    }

    @PutMapping
    public void updateMenu(@RequestBody AdminMenuDto adminMenuDto) {
        menuService.saveMenu(adminMenuDto);
    }

    @PutMapping(value = "/{menuSeq}/image/{onOff}", produces = "multipart/form-data")
    public void updateMenuImage(@PathVariable Long menuSeq, @RequestPart MultipartFile file,
                                @PathVariable String onOff) throws IOException {
        menuService.uploadMenuImage(menuSeq, file, onOff);
    }

    @PutMapping(value = "/sort")
    public void sortMenu(@RequestBody MenuSort menuSort) {
        menuService.sortMenu(menuSort);
    }

    @DeleteMapping(value = "/{menuSeq}")
    public void deleteMenu(@PathVariable Long menuSeq) {
        menuService.deleteMenu(menuSeq);
    }
}
