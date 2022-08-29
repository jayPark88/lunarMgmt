package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminAuthMenuDto;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LunarMgmtApplication.class)
class AdminAuthTest {

    @Autowired
    private AdminAuthRepository adminAuthRepository;
    @Autowired
    private SettingAuthAbstract settingAuthSub;
    @Autowired
    private AdminAuthMenuRepository adminAuthMenuRepository;
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void searchContainAuthNameTest(){
        // given
        String authNm = "";

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

    @Test
    @DisplayName("HQ-ADMIN 권한 메뉴 등록")
    public void saveAuthMenu(){
        // given
        List<AdminAuthMenuDto>adminUserDtos =
                adminAuthMenuRepository.findAllByAuthAuthSeqAndMenuUseYnOrderByMenuSortNumAsc(1L, 'Y').stream().map(AdminAuthMenuDto::new).collect(Collectors.toList());
        AdminUserDto adminUserDto = new AdminUserDto(settingAuthSub.selectAuthUserList(1L).stream().filter(item -> item.getAdminUserId().equals("jayParkSub")).findFirst().get().to());

        // when
        // 예상 시나리오는 adminUserDtos는 4번을 제외한 1~3번까지만 존재하고 as-is의 4번을 제외하고 3번까지만 데이터 수정되는 부분을 할 예정이다.
        settingAuthSub.saveAuthMenu(adminUserDtos.stream().filter(item -> !item.getAuthMenuSeq().equals(4L)).collect(Collectors.toList()), adminUserDto);

        // then
        assertAll(
                () -> assertTrue(adminAuthMenuRepository.findAllByAuthAuthSeqAndMenuUseYnOrderByMenuSortNumAsc(1L,'Y').stream().filter(item -> item.getCreateId().equals("jayParkSub")).findFirst().isPresent()),
                () -> assertTrue(adminAuthMenuRepository.findAllByAuthAuthSeqAndMenuUseYnOrderByMenuSortNumAsc(1L,'Y').stream().parallel().filter(item-> !item.getAuthMenuSeq().equals(4L)).findFirst().isPresent())
        );

    }

    @Test
    @DisplayName("HQ-ADMIN 권한에 속한 사용자 추가")
    public void saveAuthUsers(){
        // given
        Long authSeq = 9L;
        Long[] userSeqs = {7L};

        // when
        settingAuthSub.saveAuthUsers(authSeq, userSeqs);

        // then
        assertAll(
                () -> assertTrue(adminUserRepository.findById(userSeqs[0]).stream().filter(item -> item.getAuth().getAuthSeq().equals(9L)).findFirst().isPresent())
        );
    }

    @Test
    @DisplayName("HQ-ADMIN 권한에 속한 사용자 삭제")
    public void deleteAuthUsers(){
        // given
        Long authSeq = 9L;
        Long[] userSeqs = {7L};

        // when
        settingAuthSub.deleteAuthUsers(authSeq, userSeqs);

        // then
        assertAll(
                () -> assertTrue(ObjectUtils.isEmpty(adminUserRepository.findById(userSeqs[0]).get().getAuth()))
        );
    }
}
