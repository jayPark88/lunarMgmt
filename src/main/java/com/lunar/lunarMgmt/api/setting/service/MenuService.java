package com.lunar.lunarMgmt.api.setting.service;

import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.util.MenuUtil;
import com.lunar.lunarMgmt.common.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private SettingMenuAbstract settingMenuAbstract;
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    public List<AdminMenuDto> selectMenuList(){
        settingMenuAbstract = ac.getBean("menuUtil", MenuUtil.class);
        return settingMenuAbstract.selectMenuList();
    }
}
