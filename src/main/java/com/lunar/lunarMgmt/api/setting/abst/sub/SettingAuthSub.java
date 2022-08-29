package com.lunar.lunarMgmt.api.setting.abst.sub;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminAuthMenuDto;
import com.lunar.lunarMgmt.api.setting.model.AuthDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.api.setting.util.AuthMenuUtil;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminUserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class SettingAuthSub extends SettingAuthAbstract {

    public SettingAuthSub(AdminAuthRepository adminAuthRepository, AdminAuthMenuRepository adminAuthMenuRepository, AdminMenuRepository adminMenuRepository, AdminUserRepository adminUserRepository, AuthMenuUtil authMenuUtil) {
        super(adminAuthRepository, adminAuthMenuRepository, adminMenuRepository, adminUserRepository, authMenuUtil);
    }

    @Override
    public List<AuthDto> selectAuthList(String authNm) {
        return adminAuthRepository.findAll().stream().parallel()
                .filter(item -> item.getAuthNm().contains(authNm))
                .filter(item -> item.getUseYn().equals('Y'))
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
        if(optionalAdminAuthEntity.isPresent() && optionalAdminAuthEntity.get().getAdminUserList().size()>0){
            throw new RuntimeException("해당 권한은 adminUser에서 사용 중입니다.");
        }else{
            adminAuthRepository.deleteById(authSeq);
        }
    }

    @Override
    public List<AdminUserDto> selectAuthUserList(Long authSeq) {
        List<AdminUserEntity> adminUserEntityList = new ArrayList<>();
        Optional<AdminAuthEntity>optionalAdminAuthEntity = adminAuthRepository.findById(authSeq);
        if(optionalAdminAuthEntity.isPresent()){
            adminUserEntityList = optionalAdminAuthEntity.get().getAdminUserList();
        }
        return adminUserEntityList.stream().map((item) -> new AdminUserDto(item).withoutPasswd()).collect(Collectors.toList());
    }

    @Override
    public List<VueMenuDto> selectMenuAuthMenutree(Long authSeq) {
        //권한 메뉴에서 권한에 등록할 메뉴 리스트 가져오기
        List<VueMenuDto> menus = authMenuUtil.selectAuthVueMenuList(authSeq);

        // 시작은 최상위 부모, 부모아이디가 자신인 메뉴들 ( parentmenuId == menuId )
        // 두개의 데이터들로 부모, 자식 구별하여 list에 재구성하여 반환 시켜준다.
        return authMenuUtil.createVueMenuTree(
                menus.stream().filter((m) -> m.getData().getParentMenuId() == 0)
                        .collect(Collectors.toList()),
                menus);
    }

    @Override
    public void saveAuthMenu(List<AdminAuthMenuDto> adminAuthMenuDtos, AdminUserDto adminUserDto) {
        List<AdminAuthMenuEntity> saveList = new ArrayList<>();
        Long authSeq = adminAuthMenuDtos.get(0).getAuthSeq();

        // 전부 다 삭제 처리 하고 다시 추가를 한다.
        adminAuthMenuRepository.deleteByAuthAuthSeq(authSeq);
        // flush 해도 transactional
        adminAuthMenuRepository.flush();

        // stream 병렬 처리로 item element를 가지오 와서 화면에서 선택한 권한의 authSeq와
        // 수정한 adminLogin정보를 set하여 saveList에 add를 하여 다시 저장을 한다.
        // 다시 삭제하고 다시 저장하는게 제일 비용적으로 저렴한것 같다.
        adminAuthMenuDtos.stream().parallel().forEach(item -> {
            item.setAuthSeq(authSeq);
            item.setCreateInfo(adminUserDto);
            saveList.add(item.to());
        });
        adminAuthMenuRepository.saveAll(saveList);
    }

    @Override
    public void saveAuthUsers(Long authSeq, Long[] userSeqs) {
        for (Long userSeq : userSeqs) {
            Optional<AdminUserEntity>optionalAdminUserEntity = adminUserRepository.findById(userSeq);
            if(optionalAdminUserEntity.isPresent()){
                if (optionalAdminUserEntity.get().getAuth() != null)
                    throw new RuntimeException(String.format(
                            "[%s(%s)]는 이미 [%s] 권한을 가지고 있습니다.", optionalAdminUserEntity.get().getAdminUserId(), optionalAdminUserEntity.get().getAdminUserNm(),
                            optionalAdminUserEntity.get().getAuth().getAuthNm()));

                AdminUserDto adminUserDto = new AdminUserDto(optionalAdminUserEntity.get());
                adminUserDto.setAuthSeq(authSeq);

                adminUserRepository.save(adminUserDto.to());
            }
        }
    }

    @Override
    public void deleteAuthUsers(Long authSeq, Long[] userSeqs) {
        for (Long userSeq : userSeqs) {
            Optional<AdminUserEntity>optionalAdminUserEntity = adminUserRepository.findById(userSeq);
            if(optionalAdminUserEntity.isPresent()){
                AdminUserDto adminUserDto = new AdminUserDto(optionalAdminUserEntity.get());
                adminUserDto.setAuthSeq(null);

                adminUserRepository.save(adminUserDto.to());
            }
        }
    }
}
