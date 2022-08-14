package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

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
        assertAll(
                "first test",
                () -> assertEquals(authDtos.size(), 1)
        );
    }

    @Test
    @DisplayName("권한 상세 조회 테스트")
    public void searchAuthDetail(){
        // given
        Long authSeq = 1L;

        // when
        AuthDto authDto = settingAuthSub.selectAuth(authSeq);
        //then
        assertAll(
                () -> assertNotNull(authDto)
        );
    }

    @Test
    @DisplayName("권한 상세 조회 테스트")
    public void saveAuth(){
        // given
        AuthDto authDto = AuthDto.builder()
                .authCd("AUTH_GENERAL1")
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
        assertAll(
                () -> assertNotNull(adminAuthRepository.findAll().stream().filter(item -> item.getAuthCd().equals("AUTH_GENERAL1")).findFirst())
        );
    }

    @Test
    @DisplayName("권한 상세 수정 테스트")
    public void modifyAuth(){
        // given
        Long authSeq = 9L;
        // when
        Optional<AdminAuthEntity> optionalAdminAuthEntity = adminAuthRepository.findById(authSeq);
        // then
        assertAll(
                () -> assertTrue(optionalAdminAuthEntity.isPresent())
                );

        // given
        AuthDto authDto = optionalAdminAuthEntity.map(AuthDto::new).orElse(null);
        authDto.setAuthNm("서비스 관계자");
        authDto.setAuthDesc("서비스 관계자의 권한 코드 정보 입니다.");

        // when
        adminAuthRepository.save(authDto.to());

        // then
        Optional<AdminAuthEntity> testOptionalAdminAuthEntity = adminAuthRepository.findById(authSeq);
        assertAll(
                () -> assertTrue(testOptionalAdminAuthEntity.isPresent())
                ,() -> assertTrue(testOptionalAdminAuthEntity.get().getAuthNm().equals("서비스 관계자"))
                ,() -> assertTrue(testOptionalAdminAuthEntity.get().getAuthDesc().contains("서비스 관계자의"))
        );

    }

    @Test
    @DisplayName("HQ-ADMIN 권한 삭제 테스트")
    public void deleteAuth(){
        // given
        Long authSeq = 1L;

        // when
        settingAuthSub.deleteAuth(authSeq);

        // then
        assertAll(
                () -> assertFalse(adminAuthRepository.findById(authSeq).isPresent())
        );
    }

    @Test
    @DisplayName("해당 권한에 속한 사용자 리스트 조회")
    public void selectAuthUserList(){
        // given
        Long authSeq = 1L;

        // when
        List<AdminUserDto> adminUserDtos = settingAuthSub.selectAuthUserList(authSeq);

        // then
        assertAll(
                () -> assertTrue(adminUserDtos.size()>0)
        );
    }
    
    @Test
    @DisplayName("해당 군한에 속한 메뉴 리스트 조회")
    public void selectMenuAuthList(){
        // given
        Long authSeq = 1L;
        AtomicInteger index = new AtomicInteger();

        // when
        List<VueMenuDto> vueMenuDtos = settingAuthSub.selectMenuAuthMenutree(authSeq);
        vueMenuDtos.forEach(item -> {
            index.getAndIncrement();
            System.out.println("vueMenuDtos : "+index+" = "+item);
        });

        // when
        assertAll(
                ()-> assertTrue(vueMenuDtos.size()>0)
        );

    }
}
