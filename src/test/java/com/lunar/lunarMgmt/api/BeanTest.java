package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.abst.sub.SettingMenuSub;
import com.lunar.lunarMgmt.common.config.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.assertj.core.api.Assertions;

public class BeanTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        SettingMenuAbstract settingMenuAbstract =ac.getBean("menuUtil", SettingMenuAbstract.class);
        Assertions.assertThat(settingMenuAbstract).isInstanceOf(SettingMenuSub.class);
    }
}
