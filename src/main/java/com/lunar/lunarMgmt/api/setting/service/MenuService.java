package com.lunar.lunarMgmt.api.setting.service;

import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final SettingMenuAbstract settingMenuAbstract;
    public List<AdminMenuDto> selectMenuList(){
        return settingMenuAbstract.selectMenuList();
    }
}
