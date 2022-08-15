package com.lunar.lunarMgmt.api.setting.controller;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.model.AdminAuthMenuDto;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.api.setting.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/setting/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/list")
    public List<AuthDto> selectAuthList(@ModelAttribute String authNm) {
        return authService.selectAuthList(authNm);
    }

    @GetMapping("/{authSeq}")
    public AuthDto selectAuth(@PathVariable Long authSeq) {
        return authService.selectAuth(authSeq);
    }

    @PostMapping("/save")
    public void saveAuth(@RequestBody AuthDto authDto) {
        authService.saveAuth(authDto);
    }

    @PutMapping("/update")
    public void updateAuth(@RequestBody AuthDto authDto) {
        authService.saveAuth(authDto);
    }

    @DeleteMapping("/delete/{authSeq}")
    public void deleteAuth(@PathVariable Long authSeq) {
        authService.deleteAuth(authSeq);
    }

    @GetMapping("/users/{authSeq}")
    public List<AdminUserDto> selectAuthUserList(@PathVariable Long authSeq) {
        return authService.selectAuthUserList(authSeq);
    }

    @GetMapping("/menus/{authSeq}")
    public List<VueMenuDto> selectMenuAuthList(@PathVariable Long authSeq) {
        return authService.selectMenuAuthMenutree(authSeq);
    }

    @PostMapping("/menus")
    public void saveAuthMenu(@RequestBody List<AdminAuthMenuDto> authMenuDtos, @AuthenticationPrincipal AdminUserDto adminUserDto){
        authService.saveAuthMenu(authMenuDtos, adminUserDto);
    }

    @PostMapping(value = "/users/{authSeq}")
    public void saveAuthUsers(@PathVariable Long authSeq, @RequestBody Long[] userSeqList) {
        authService.saveAuthUsers(authSeq, userSeqList);
    }

    @DeleteMapping(value = "/users/{authSeq}")
    public void deleteAuthUsers(@PathVariable Long authSeq, @RequestBody Long[] userSeqList) {
        authService.deleteAuthUsers(authSeq, userSeqList);
    }

}
