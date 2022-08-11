package com.lunar.lunarMgmt.api.setting.api.service;

import com.lunar.lunarMgmt.api.setting.api.abst.sub.SettingAuthSub;
import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final SettingAuthSub settingAuthSub;
    public List<AuthDto> selectAuthList(String authNm){
        return settingAuthSub.selectAuthList(authNm);
    }
}
