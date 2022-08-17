package com.lunar.lunarMgmt.api.setting.service;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
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

    public List<AdminMenuDto> selectMenuTree(AdminUserDto adminUserDto){
        return settingMenuAbstract.selectMenuTree(adminUserDto);
    }

    public List<VueMenuDto> selectVueMenuTree(){
        return settingMenuAbstract.selectVueMenuTree();
    }
}
