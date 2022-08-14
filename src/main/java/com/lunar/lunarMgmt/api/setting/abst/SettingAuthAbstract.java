package com.lunar.lunarMgmt.api.setting.abst;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.api.setting.util.AuthMenuUtil;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class SettingAuthAbstract {
    protected final AdminAuthRepository adminAuthRepository;
    protected final AdminAuthMenuRepository adminAuthMenuRepository;
    protected final AdminMenuRepository adminMenuRepository;
    protected final AuthMenuUtil authMenuUtil;

    // 권한 리스트 조회
    public abstract List<AuthDto> selectAuthList(String authNm);

    // 권한 상세 정보 조회
    public abstract AuthDto selectAuth(Long authSeq);

    // 권한 신규 생성
    public abstract void saveAuth(AuthDto authDto);

    // 권한 삭제
    public abstract void deleteAuth(Long authSeq);

    // 해당 권한에 속한 사용자 리스트 조회
    public abstract List<AdminUserDto> selectAuthUserList(Long authSeq);
    
    // 해당 권한의 속한 메뉴 리스트 조회
    public abstract List<VueMenuDto> selectMenuAuthList(Long authSeq);

}