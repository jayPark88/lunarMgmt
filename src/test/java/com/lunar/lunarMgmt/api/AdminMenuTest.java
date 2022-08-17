package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
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
    public void selectMenuList(){
        // given 해당 테스트는 조건이 없다.

        // when
        List<AdminMenuDto> adminMenuDtos = settingMenuAbstract.selectMenuList();
        adminMenuDtos.stream().parallel().forEach(item ->{
            System.out.println(item);
        });

        // then
        assertAll(
                () -> assertTrue(adminMenuDtos.size()>0)
        );

    }

    @Test
    @DisplayName("메뉴 트리 조회")
    public void selectMenuTree(){
        // given
        Long authSeq = 1L;
        List<AdminUserDto> adminUserDtos = settingAuthAbstract.selectAuthUserList(authSeq);

        // when
        List<AdminMenuDto> adminMenuDtos = settingMenuAbstract.selectMenuTree(adminUserDtos.get(0));
        adminMenuDtos.removeIf((menuDto) -> menuDto.getPageUrl().equals("/")); // 메인화면 이 있으면 그건 NavigationMenu에서 제거
        // then
        assertAll(
                () -> assertTrue(adminMenuDtos.size() >0)
        );
    }
    @Test
    @DisplayName("vue메뉴 트리 조회")
    public void selectVueMenuTree(){
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
}
