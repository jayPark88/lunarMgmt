package com.lunar.lunarMgmt.api.setting.service;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SettingAuthAbstract settingAuthSub;

    public List<AuthDto> selectAuthList(String authNm) {
        return settingAuthSub.selectAuthList(authNm);
    }

    public AuthDto selectAuth(Long authSeq) {
        return settingAuthSub.selectAuth(authSeq);
    }

    public void saveAuth(AuthDto authDto){
        settingAuthSub.saveAuth(authDto);
    }

    public void deleteAuth(Long authSeq){
        settingAuthSub.deleteAuth(authSeq);
    }

    public List<AdminUserDto> selectAuthUserList(Long authSeq){
        return settingAuthSub.selectAuthUserList(authSeq);
    }
}
