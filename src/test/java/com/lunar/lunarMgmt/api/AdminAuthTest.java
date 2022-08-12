package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.setting.api.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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

    @Test
    @DisplayName("권한 상세 조회 테스트")
    public void searchAuthDetail(){
        // given
        Long authSeq = 0L;

        // when
        AuthDto authDto = new AuthDto();
        authDto = settingAuthSub.selectAuth(authSeq);

        //then
        Assertions.assertThat(authDto).hashCode();
    }

    @Test
    @DisplayName("권한 상세 조회 테스트")
    public void saveAuth(){
        // given
        AuthDto authDto = AuthDto.builder()
                .authCd("AUTH_GENERAL")
                .authNm("일반 관리자")
                .authDesc("일반 관리자")
                .useYn('Y')
                .createId("jayPark")
                .createNm("박지훈")
                .createDatetime(LocalDateTime.now())
                .modifyId("jayPark")
                .modifyNm("박지훈")
                .modifyDatetime(LocalDateTime.now()).build();

        // when
        adminAuthRepository.save(authDto.to());

        // then
        Assertions.assertThat(adminAuthRepository.findAll().stream().filter(item -> item.getAuthCd().equals("AUTH_GENERAL")).findFirst()).isNotEmpty();
    }
}
