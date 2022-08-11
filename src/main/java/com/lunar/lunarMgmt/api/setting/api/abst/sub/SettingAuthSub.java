package com.lunar.lunarMgmt.api.setting.api.abst.sub;

import com.lunar.lunarMgmt.api.setting.api.abst.SettingAuthAbstract;
import com.lunar.lunarMgmt.api.setting.api.model.AuthDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SettingAuthSub extends SettingAuthAbstract {

    public SettingAuthSub(AdminAuthRepository adminAuthRepository) {
        super(adminAuthRepository);
    }

    @Override
    public List<AuthDto> selectAuthList(String authNm) {
        return adminAuthRepository.findAll().stream().parallel().filter(item -> item.getAuthNm().contains(authNm))
                .map(AuthDto::new).collect(Collectors.toList());
    }
}
