package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
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
}
