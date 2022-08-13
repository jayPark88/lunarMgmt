package com.lunar.lunarMgmt.api.setting.api.controller;

import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.api.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    public void updateAuth(@RequestBody AuthDto authDto){
        authService.saveAuth(authDto);
    }

    @DeleteMapping("/delete/{authSeq}")
    public void deleteAuth(@PathVariable Long authSeq){
        authService.deleteAuth(authSeq);
    }
}
