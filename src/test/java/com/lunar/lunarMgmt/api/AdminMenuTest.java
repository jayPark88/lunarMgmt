package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LunarMgmtApplication.class)
public class AdminMenuTest {

    @Autowired
    SettingMenuAbstract settingMenuAbstract;
    @Autowired
    SettingAuthAbstract settingAuthAbstract;
    @Autowired
    AdminMenuRepository adminMenuRepository;

    @Test
    @DisplayName("권한 없이 모든 메뉴 조회 테스트")
    public void selectMenuList() {
        // given 해당 테스트는 조건이 없다.

        // when
        List<AdminMenuDto> adminMenuDtos = settingMenuAbstract.selectMenuList();
        adminMenuDtos.stream().parallel().forEach(item -> {
            System.out.println(item);
        });

        // then
        assertAll(
                () -> assertTrue(adminMenuDtos.size() > 0)
        );

    }

    @Test
    @DisplayName("메뉴 트리 조회")
    public void selectMenuTree() {
        // given
        Long authSeq = 1L;
        List<AdminUserDto> adminUserDtos = settingAuthAbstract.selectAuthUserList(authSeq);

        // when
        List<AdminMenuDto> adminMenuDtos = settingMenuAbstract.selectMenuTree(adminUserDtos.get(0));
        adminMenuDtos.removeIf((menuDto) -> menuDto.getPageUrl().equals("/")); // 메인화면 이 있으면 그건 NavigationMenu에서 제거
        // then
        assertAll(
                () -> assertTrue(adminMenuDtos.size() > 0)
        );
    }

    @Test
    @DisplayName("vue메뉴 트리 조회")
    public void selectVueMenuTree() {
        // given 없음

        // when
        List<VueMenuDto> vueMenuDtos = settingMenuAbstract.selectVueMenuTree();

        // when
        assertAll(
                () -> assertTrue(vueMenuDtos.size() > 0)
        );
    }

    @Test
    @DisplayName("메뉴 정보 조회")
    public void selectMenu() throws Exception {
        // given
        Long menuSeq = 1L;

        // when
        AdminMenuDto adminMenuDto = settingMenuAbstract.selectMenu(menuSeq);

        //then
        assertAll(
                () -> assertTrue(adminMenuDto.getMenuNm().equals(adminMenuRepository.findById(1L).get().getMenuNm()))
        );
    }

    @Test
    @DisplayName("메뉴 정보 저장")
    public void saveMenu(){
        // given
        AdminMenuDto adminMenuDto = AdminMenuDto.builder()
                .menuNm("시스템 관리")
                .pageUrl("/system")
                .menuLevel(1)
                .parentMenuId(0L)
                .topMenuId(0L)
                .menuDesc("시스템 관리")
                .useYn('Y')
                .sortNum(5)
                .readYn('N')
                .writeYn('N').build();

        // when
        settingMenuAbstract.saveMenu(adminMenuDto);

        // then
        assertAll(
                () -> assertTrue(adminMenuRepository.findAll().stream().parallel().filter(item -> item.getMenuNm().equals("시스템 관리")).findFirst().isPresent())
        );
    }

    @Test
    @DisplayName("메뉴 정보 수정")
    public void updateMenu(){
        // given
        AdminMenuDto adminMenuDto = new AdminMenuDto(adminMenuRepository.findById(5L).get());
        adminMenuDto.setMenuNm("시스템 관리");

        // when
        AdminMenuEntity adminMenuEntity = adminMenuRepository.save(adminMenuDto.to());

        // then
        assertAll(
                () -> assertTrue(adminMenuEntity.getMenuNm().equals("시스템 관리"))
        );
    }
    
}
