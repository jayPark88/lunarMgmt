//package com.lunar.lunarMgmt.api.setting.util;
//
//import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
//import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
//import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
//import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
//import com.lunar.lunarMgmt.common.jpa.repository.FileRepository;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//@Component
//@Transactional
//public class Test extends SettingMenuAbstract {
//    public Test(AdminMenuRepository adminMenuRepository, AdminAuthMenuRepository adminAuthMenuRepository, FileRepository fileRepository) {
//        super(adminMenuRepository, adminAuthMenuRepository, fileRepository);
//    }
//
//    @Override
//    public List<AdminMenuDto> selectMenuList() {
//        System.out.println("test");
//        return null;
//    }
//}
