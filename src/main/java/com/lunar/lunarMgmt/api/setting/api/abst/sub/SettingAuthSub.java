package com.lunar.lunarMgmt.api.setting.api.abst.sub;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.api.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class SettingAuthSub extends SettingAuthAbstract {

    public SettingAuthSub(AdminAuthRepository adminAuthRepository) {
        super(adminAuthRepository);
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
}
