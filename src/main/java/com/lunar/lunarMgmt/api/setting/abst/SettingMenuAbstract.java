package com.lunar.lunarMgmt.api.setting.abst;

import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class SettingMenuAbstract {
    protected final AdminMenuRepository adminMenuRepository;
    protected final AdminAuthMenuRepository adminAuthMenuRepository;
    protected final FileRepository fileRepository;

    // 권한없이 Menu 전체 가져오기
    public abstract List<AdminMenuDto> selectMenuList();

}
