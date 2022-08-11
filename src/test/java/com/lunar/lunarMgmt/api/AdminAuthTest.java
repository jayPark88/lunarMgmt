package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.setting.api.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = LunarMgmtApplication.class)
class AdminAuthTest {

    @Autowired
    private AdminAuthRepository adminAuthRepository;
    @Autowired
    private SettingAuthAbstract settingAuthSub;

    @Test
    public void searchContainAuthNameTest(){
        // given
        String authNm = "시스";

        // when
        List<AuthDto> authDtos = settingAuthSub.selectAuthList(authNm);

        // then
        Assertions.assertThat(authDtos).hasSize(1);
    }
}
