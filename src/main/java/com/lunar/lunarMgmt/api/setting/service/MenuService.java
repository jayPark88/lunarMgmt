package com.lunar.lunarMgmt.api.setting.service;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.MenuSort;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public AdminMenuDto selectMenu(Long menuSeq) throws Exception {
        return settingMenuAbstract.selectMenu(menuSeq);
    }

    public void saveMenu(AdminMenuDto adminMenuDto){
        settingMenuAbstract.saveMenu(adminMenuDto);
    }

    public void uploadMenuImage(Long menuSeq, MultipartFile file, String onOff) throws IOException {
        settingMenuAbstract.uploadMenuImage(menuSeq, file, onOff);
    }

    public void sortMenu( MenuSort menuSort) {
        settingMenuAbstract.sortMenu(menuSort);
    }

    public void deleteMenu(Long menuSeq){
        settingMenuAbstract.deleteMenu(menuSeq);
    }
}
