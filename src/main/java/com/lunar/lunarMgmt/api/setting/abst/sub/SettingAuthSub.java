package com.lunar.lunarMgmt.api.setting.abst.sub;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.api.setting.util.AuthMenuUtil;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class SettingAuthSub extends SettingAuthAbstract {

    public SettingAuthSub(AdminAuthRepository adminAuthRepository, AdminAuthMenuRepository adminAuthMenuRepository, AdminMenuRepository adminMenuRepository, AuthMenuUtil authMenuUtil) {
        super(adminAuthRepository, adminAuthMenuRepository, adminMenuRepository, authMenuUtil);
    }

    @Override
    public List<AuthDto> selectAuthList(String authNm) {
        return adminAuthRepository.findAll().stream().parallel().filter(item -> item.getAuthNm().contains(authNm))
                .map(AuthDto::new).collect(Collectors.toList());
    }
    @Override
    public AuthDto selectAuth(Long authSeq) {
        return adminAuthRepository.findById(authSeq).map(AuthDto::new).orElse(null);
    }

    @Override
    public void saveAuth(AuthDto authDto) {
        adminAuthRepository.save(authDto.to());
    }

    @Override
    public void deleteAuth(Long authSeq) {
        Optional<AdminAuthEntity>optionalAdminAuthEntity = adminAuthRepository.findById(authSeq);
        if(optionalAdminAuthEntity.get().getAdminUserList().size()>0){
            throw new RuntimeException("해당 권한은 adminUser에서 사용 중입니다.");
        }else{
            adminAuthRepository.deleteById(authSeq);
        }
    }

    @Override
    public List<AdminUserDto> selectAuthUserList(Long authSeq) {
        List<AdminUserEntity> adminUserEntityList = adminAuthRepository.findById(authSeq).get().getAdminUserList();
        return adminUserEntityList.stream().map((item) ->{
            return new AdminUserDto(item).withoutPasswd();
        }).collect(Collectors.toList());
    }

    @Override
    public List<VueMenuDto> selectMenuAuthList(Long authSeq) {
        //권한 메뉴에서 권한에 등록할 메뉴 리스트 가져오기
        List<VueMenuDto> menus = authMenuUtil.selectAuthVueMenuList(authSeq);

        // 시작은 최상위 부모, 부모아이디가 자신인 메뉴들 ( parentmenuId == menuId )
        // 두개의 데이터들로 부모, 자식 구별하여 list에 재구성하여 반환 시켜준다.
        return authMenuUtil.createVueMenuTree(
                menus.stream().filter((m) -> m.getData().getParentMenuId() == 0)
                        .collect(Collectors.toList()),
                menus);
    }
}
