package com.lunar.lunarMgmt.api.setting.api.abst;

import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class SettingAuthAbstract {
    protected final AdminAuthRepository adminAuthRepository;

    // 권한 리스트 조회
    public abstract List<AuthDto> selectAuthList(String authNm);

    // 권한 상세 정보 조회
    public abstract AuthDto selectAuth(Long authSeq);

}
