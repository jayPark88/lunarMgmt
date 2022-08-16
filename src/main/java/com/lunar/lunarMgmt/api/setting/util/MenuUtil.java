package com.lunar.lunarMgmt.api.setting.util;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingMenuAbstract;
import com.lunar.lunarMgmt.api.setting.model.AdminMenuDto;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import com.lunar.lunarMgmt.common.jpa.repository.FileRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class MenuUtil extends SettingMenuAbstract {
    public MenuUtil(AdminMenuRepository adminMenuRepository, AdminAuthMenuRepository adminAuthMenuRepository, FileRepository fileRepository) {
        super(adminMenuRepository, adminAuthMenuRepository, fileRepository);
    }

    // 메뉴화면에서 메뉴를 수정,등록,삭제 하기 위한 VueMenu 리스트 가져오기
    // menu자체 정보를 조회
    public List<VueMenuDto> selectVueMenuList(Character useYn) {
        if (useYn == null) {
            return adminMenuRepository.findAll(Sort.by(Sort.Direction.ASC, "sortNum")).stream()
                    .map(item -> new VueMenuDto(item)).collect(Collectors.toList());
        } else {
            return adminMenuRepository.findAllByUseYn(useYn, Sort.by(Sort.Direction.ASC, "sortNum")).stream()
                    .map(item -> new VueMenuDto(item)).collect(Collectors.toList());
        }
    }

    @Override
    public List<AdminMenuDto> selectMenuList() {
        return adminMenuRepository.findAll(Sort.by(Sort.Direction.ASC, "sortNum")).stream()
                .map((menuEntity) -> new AdminMenuDto(menuEntity)).collect(Collectors.toList());
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
                .map((ame) -> new AdminMenuDto(ame)).collect(Collectors.toList());
    }

    // 부모 메뉴 아이디로 이루어진 list와 authSeq로 이루어진 부모, 자식 구별없는 list으로 최종 메뉴 트리 생성 method
    private List<AdminMenuDto> createMenuTree(List<AdminMenuDto> adminMenuDtos, List<AdminMenuDto> allMenus) {
        // 부모 menuList Loop문을 돌려서
        for (int i = 0; i < adminMenuDtos.size(); i++) {
            // 부모 메뉴 아이디 element를 하나 추출하여
            // 이렇게 하면 adminMenuDtos.get(i)의 element의 메모리 주소값을 새로 생성된 AdminMenuDto가 참조하기에 set해주면 adminMenuDtos의 element에 결과가 반영된다.
            AdminMenuDto adminMenuDto = adminMenuDtos.get(i);

            // 그 부모에 속해 있는 자식 메뉴 아이디 list 생성 비즈니스 로직
            List<AdminMenuDto> children = allMenus.stream().filter(
                            // 부모 자식 구별없는 list의 element중에 부모 아이디가 0이 아니고, element의 부모 메뉴 아이디가 부모 메뉴 리스트의 아이디와 일치 한 것만 filter
                            item -> item.getParentMenuId() != 0 && item.getParentMenuId() == adminMenuDto.getMenuSeq()
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
}
