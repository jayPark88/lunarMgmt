package com.lunar.lunarMgmt.api.setting.abst;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.config.yml.MenuFileConfig;
import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class SettingMenuAbstract {
    protected final AdminMenuRepository adminMenuRepository;
    protected final AdminAuthMenuRepository adminAuthMenuRepository;
    protected final FileRepository fileRepository;
    protected final MenuFileConfig menuFileConfig;
    protected final FileUtil fileUtil;

    // 권한없이 Menu 전체 가져오기
    public abstract List<AdminMenuDto> selectMenuList();

    // 메뉴 트리 조회
    public abstract List<AdminMenuDto> selectMenuTree( AdminUserDto adminUserDto);

    // 권한으로 Menu 가져오기 + 읽기, 쓰기 권한
    public abstract List<AdminMenuDto> selectMenuList(AdminUserDto adminUserDto);

    // Vue 메뉴 트리 조회
    public abstract List<VueMenuDto> selectVueMenuTree();

    // 메뉴 정보 조회(단일 조회)
    public abstract AdminMenuDto selectMenu(Long menuSeq) throws Exception;

    // 메뉴 정보 저장
    public abstract void saveMenu(AdminMenuDto adminMenuDto);

    // 메뉴 정렬 순서 변경
    public abstract void uploadMenuImage(Long menuSeq, MultipartFile file, String onOff) throws IOException;
}
