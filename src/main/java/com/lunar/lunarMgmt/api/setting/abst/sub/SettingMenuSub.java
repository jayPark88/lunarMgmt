package com.lunar.lunarMgmt.api.setting.abst.sub;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.MenuSort;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.api.setting.util.MenuFileUtil;
import com.lunar.lunarMgmt.common.config.yml.MenuFileConfig;
import com.lunar.lunarMgmt.common.intf.FileUtil;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import com.lunar.lunarMgmt.common.jpa.entities.FileEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.FileRepository;
import com.lunar.lunarMgmt.common.model.FileDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class SettingMenuSub extends SettingMenuAbstract {

    public SettingMenuSub(AdminMenuRepository adminMenuRepository, AdminAuthMenuRepository adminAuthMenuRepository, FileRepository fileRepository, MenuFileConfig menuFileConfig, FileUtil fileUtil) {
        super(adminMenuRepository, adminAuthMenuRepository, fileRepository, menuFileConfig, fileUtil);
    }

    // 메뉴화면에서 메뉴를 수정,등록,삭제 하기 위한 VueMenu 리스트 가져오기
    // menu자체 정보를 조회
    public List<VueMenuDto> selectVueMenuList(Character useYn) {
        if (useYn == null) {
            return adminMenuRepository.findAll(Sort.by(Sort.Direction.ASC, "sortNum")).stream()
                    .map(VueMenuDto::new).collect(Collectors.toList());
        } else {
            return adminMenuRepository.findAllByUseYn(useYn, Sort.by(Sort.Direction.ASC, "sortNum")).stream()
                    .map(VueMenuDto::new).collect(Collectors.toList());
        }
    }

    @Override
    public List<AdminMenuDto> selectMenuList() {
        return adminMenuRepository.findAll(Sort.by(Sort.Direction.ASC, "sortNum")).stream()
                .map(AdminMenuDto::new).collect(Collectors.toList());
    }

    @Override
    public List<AdminMenuDto> selectMenuTree(AdminUserDto adminUserDto) {
        List<AdminMenuDto> menus = selectMenuList(adminUserDto);

        // 시작은 최상위 부모, 부모아이디가 자신인 메뉴들 ( parentmenuId == menuId ), authSeq로 이루어진 menuList들
        return createMenuTree(menus.stream().filter((m) -> m.getParentMenuId() == 0)
                .collect(Collectors.toList()), menus);
    }

    @Override
    public List<AdminMenuDto> selectMenuList(AdminUserDto adminUserDto) {
        return adminAuthMenuRepository.selectAllByAuth(adminUserDto.getAuthSeq()).stream()
                .map(AdminMenuDto::new).collect(Collectors.toList());
    }

    @Override
    public List<VueMenuDto> selectVueMenuTree() {
        List<VueMenuDto> menus = selectVueMenuList(null);
        // 시작은 최상위 부모, 부모아이디가 자신인 메뉴들 ( parentmenuId == menuId ), 모든 메뉴들
        return createVueMenuTree(
                menus.stream().filter((m) -> m.getData().getParentMenuId() == 0)
                        .collect(Collectors.toList()),
                menus);
    }

    @Override
    public AdminMenuDto selectMenu(Long menuSeq) throws Exception {
        Optional<AdminMenuEntity> optionalAdminMenuEntity = adminMenuRepository.findById(menuSeq);
        if (optionalAdminMenuEntity.isPresent()) {
            return new AdminMenuDto(optionalAdminMenuEntity.get());
        } else {
            throw new Exception("메뉴를 찾지 못했습니다.");
        }
    }

    @Override
    public void saveMenu(AdminMenuDto adminMenuDto) {
        // update 일 경우
        if (!Objects.isNull(adminMenuDto.getMenuSeq()) && adminMenuDto.getUseYn().equals('N')) {
            // update일 경우 사용여부 'N' 이면 권한에 연결되어있는지 확인부터 해야한다.
            // 권한에 연결되어 있을 경우 사용 중 이므로 변경 하면 안된다.
            List<AdminAuthMenuEntity> usedAuthMenuList = adminAuthMenuRepository.findAllByMenuMenuSeq(adminMenuDto.getMenuSeq());

            if (usedAuthMenuList.size() > 0) {
                List<String> authNmList = usedAuthMenuList.stream()
                        .map((authMenu) -> String.format("[%s]", authMenu.getAuth().getAuthNm())).collect(Collectors.toList());
                throw new RuntimeException(String.format("%s 권한에서 사용중이므로\n 사용 여부를 변경하실 수 없습니다.", String.join(",", authNmList)));
            }
        }

        // 저장 결과를 savedEntity에 담아서
        AdminMenuEntity savedEntity = adminMenuRepository.save(adminMenuDto.to());

        // topMenuId update 해당 분기문은 부모 메뉴 최초 등록 시에만 해당되어 알고리즘이 실행된다.
        // topMenuId를 menuSeq아이디로 set을 해주기 위한.
        if (savedEntity.getParentMenuId() == 0 && savedEntity.getTopMenuId() == 0) {
            savedEntity.updateTopMenuId(savedEntity.getMenuSeq());
            adminMenuRepository.save(savedEntity);
        }
    }

    @Override
    public void uploadMenuImage(Long menuSeq, MultipartFile file, String onOff) throws IOException {
        // 파일 업로드
        fileUtil.uploadFile(file, menuFileConfig.getUploadPath()+file.getOriginalFilename());

        // 파일 업로드 결과 저장
        FileDto fileDto =
                fileUtil.createFileDto(file, menuFileConfig.getUploadPath()+file.getOriginalFilename());

        FileEntity fileEntity = fileRepository.save(fileDto.to());
        AdminMenuEntity adminMenuEntity = adminMenuRepository.findById(menuSeq).get();

        FileEntity prevFileEntity = null;

        switch (onOff.toLowerCase()) {
            case "on":
                prevFileEntity = adminMenuEntity.getOnImageFile();
                adminMenuEntity.setOnImageFile(fileEntity);
                break;
            case "off":
                prevFileEntity = adminMenuEntity.getOffImageFile();
                adminMenuEntity.setOffImageFile(fileEntity);
                break;
        }

        // 새로운 image로 update
        adminMenuRepository.saveAndFlush(adminMenuEntity);

        // 모든과정이 정상적으로 끝나면, 이전 image 삭제 및 file deleteYn 'Y'
        if (prevFileEntity != null) {
            FileDto prevFileDto = new FileDto(prevFileEntity);
            prevFileDto.setDeleteYn('Y');

            // 이전 파일 데이터 삭제
            fileRepository.delete(prevFileDto.to());
            // 이전 파일 물리적 삭제
            File deletePrevFile = new File(prevFileDto.getFilePath());
            deletePrevFile.delete();
        }
    }

    @Override
    public void sortMenu(MenuSort menuSort) {
        List<AdminMenuEntity> sortMenus;
        // 같은 부모 메뉴의 하위 메뉴들 정렬 순서 변경
        // 수정할 sortNum이 asis sortNum보다 작을 때 ex) 1, 3
        if (menuSort.getSortNum() < menuSort.getBeforeSortNum()) {
            sortMenus =
                    adminMenuRepository.findByParentMenuIdAndMenuSeqNotAndSortNumBetween(menuSort.getParentMenuId(),
                            menuSort.getParentMenuId(), menuSort.getSortNum(), menuSort.getBeforeSortNum() - 1);
            sortMenus = sortMenus.stream().map((menu) -> {
                AdminMenuDto md = new AdminMenuDto(menu);
                md.setSortNum(menu.getSortNum() + 1);
                return md.to();
            }).collect(Collectors.toList());
        } else {
            sortMenus =
                    adminMenuRepository.findByParentMenuIdAndMenuSeqNotAndSortNumBetween(menuSort.getParentMenuId(),
                            menuSort.getParentMenuId(), menuSort.getBeforeSortNum() + 1, menuSort.getSortNum());

            sortMenus = sortMenus.stream().map((menu) -> {
                AdminMenuDto md = new AdminMenuDto(menu);
                md.setSortNum(menu.getSortNum() - 1);
                return md.to();
            }).collect(Collectors.toList());
        }

        List<AdminMenuEntity> savedList = adminMenuRepository.saveAll(sortMenus);

        // 변경이 되었으면
        if (savedList.size() > 0) {
            // 변경 할 메뉴의 정렬 순서 변경
            AdminMenuEntity entity = adminMenuRepository.findById(menuSort.getMenuSeq()).get();
            AdminMenuDto dto = new AdminMenuDto(entity);
            dto.setSortNum(menuSort.getSortNum());

            adminMenuRepository.save(dto.to());
        }
    }

    @Override
    public void deleteMenu(Long menuSeq) {
        AdminMenuEntity menuEntity = adminMenuRepository.findById(menuSeq).get();
        int lastSortNum = adminMenuRepository.selectLastSortNumByParentMenuId(menuEntity.getParentMenuId());

        MenuSort menuSort = new MenuSort(menuEntity.getParentMenuId(), menuEntity.getMenuSeq(), lastSortNum, menuEntity.getSortNum());
        // 삭제 전 sortNum을 맞춰주기 위해 맨 마지막으로 보낸다
        sortMenu(menuSort);

        FileEntity onFile = menuEntity.getOnImageFile();
        if (!ObjectUtils.isEmpty(onFile)) {
            onFile.updateDeleteYn('N');
        }
        FileEntity offFile = menuEntity.getOffImageFile();
        if (!ObjectUtils.isEmpty(offFile)) {
            offFile.updateDeleteYn('N');
        }

        try {
            adminMenuRepository.delete(menuEntity);
            adminMenuRepository.flush();
        } catch (RuntimeException e) {
            throw new RuntimeException("권한에 등록되어있는 메뉴입니다.\n권한에서 제거 후 삭제해 주십시오.");
        }

        if (!ObjectUtils.isEmpty(onFile)) {
            fileRepository.delete(onFile);
            File deleteFile = new File(onFile.getFilePath());
            deleteFile.delete();
        }
        if (!ObjectUtils.isEmpty(offFile)) {
            fileRepository.delete(offFile);
            File deleteFile = new File(offFile.getFilePath());
            deleteFile.delete();
        }
    }

    // 부모 메뉴 아이디로 이루어진 list와 authSeq로 이루어진 부모, 자식 구별없는 list으로 최종 메뉴 트리 생성 method
    private List<AdminMenuDto> createMenuTree(List<AdminMenuDto> adminMenuDtos, List<AdminMenuDto> allMenus) {
        // 부모 menuList Loop문을 돌려서
        for (AdminMenuDto adminMenuDto : adminMenuDtos) {
            // 부모 메뉴 아이디 element를 하나 추출하여
            // 이렇게 하면 adminMenuDtos.get(i)의 element의 메모리 주소값을 새로 생성된 AdminMenuDto가 참조하기에 set해주면 adminMenuDtos의 element에 결과가 반영된다.
            // 그 부모에 속해 있는 자식 메뉴 아이디 list 생성 비즈니스 로직
            List<AdminMenuDto> children = allMenus.stream().filter(
                    // 부모 자식 구별없는 list의 element중에 부모 아이디가 0이 아니고, element의 부모 메뉴 아이디가 부모 메뉴 리스트의 아이디와 일치 한 것만 filter
                    item -> item.getParentMenuId() != 0 && Objects.equals(item.getParentMenuId(), adminMenuDto.getMenuSeq())
            ).collect(Collectors.toList());

            // 재귀 호출하여 child에 하위 child가 있는지 체크도 하고 없으면
            if (children.size() > 0) {
                adminMenuDto.setChildren(children);
            } else {
                // 자식 menuList가 없으면 collectionList 초기화 한것을 set
                adminMenuDto.setChildren(new ArrayList<>());
            }
        }

        return adminMenuDtos;
    }

    // VueMenu Tree 만들기
    private List<VueMenuDto> createVueMenuTree(List<VueMenuDto> menus, List<VueMenuDto> allMenus) {
        for (VueMenuDto vueMenuDto : menus) {
            List<VueMenuDto> children = allMenus.stream()
                    .filter((m) -> m.getData().getParentMenuId() != 0
                            && Objects.equals(m.getData().getParentMenuId(), vueMenuDto.getData().getMenuSeq()))
                    .collect(Collectors.toList());

            if (children.size() > 0) {
                vueMenuDto.setChildren(children);
            } else {
                vueMenuDto.setChildren(new ArrayList<>());
            }
        }

        return menus;
    }
}
