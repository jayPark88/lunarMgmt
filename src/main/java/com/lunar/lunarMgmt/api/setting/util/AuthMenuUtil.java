package com.lunar.lunarMgmt.api.setting.util;

import com.lunar.lunarMgmt.api.setting.abst.sub.SettingMenuSub;
import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import com.lunar.lunarMgmt.common.jpa.repository.AdminAuthMenuRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthMenuUtil {
    private final AdminAuthMenuRepository adminAuthMenuRepository;
    private final SettingMenuSub settingMenuSub;

    // 권한 메뉴에서 권한에 등록할 메뉴 리스트 가져오기
    public List<VueMenuDto> selectAuthVueMenuList(Long authSeq){
        // 해당 권한에 속한 사용 중인 권한 리스트 조회
        List<AdminAuthMenuEntity> authMenuList = adminAuthMenuRepository.findAllByAuthAuthSeqAndMenuUseYnOrderByMenuSortNumAsc(authSeq, 'Y');

        // <MenuSeq, {ReadYn, WriteYn}> 을 사용하기위해 Map에 넣기
        Map<Long, AdminAuthMenuEntity> authMenuMap = new HashMap<>();

        // 해당 권한한에 속한 사용 중인 권한 리스트 사이즈 만큼 loop문을 돌려서 각 element를 AdminAuthMenuentity에 넣어서
        // key에는 menuSeq, value에는 adminAuthMenuEntity를 넣는다.
        for (AdminAuthMenuEntity adminAuthMenuEntity : authMenuList) {
            authMenuMap.put(adminAuthMenuEntity.getMenu().getMenuSeq(), adminAuthMenuEntity);
        }

        // VueMenuList에 readYn, writeYn 담기, 없으면 null,
        // 최종적으로는 menuEntity에 있는 구조로 화면에 출력을 해야 하기에 메뉴를 한번 더 조회 후 adminAuth에 있는 정보와 매핑하여 가공을 하는 것이다.
        // 현재 adminMenu에 있는 사용자 유무를 통한 데이터를 그대로 new VueMenuDto(item))생성자를 통해서 data를 mapping을 한다.
        List<VueMenuDto> allVueMenus = settingMenuSub.selectVueMenuList('Y');

        allVueMenus.forEach((item) -> {
            // adminAuthMenuEntity에 item의 menuSeq와 설정>메뉴의 리스트의 menuSeq와 일치하는 정보를 가지고 와서 setting을 해줌.
            // adminAuth에 속한 메뉴와 설정>menu와 일치한 것만을 가지고 와서 readYn, writeYn정보를 setting을 해준다
            AdminAuthMenuEntity ame = authMenuMap.get(item.getData().getMenuSeq());
            if (ame != null) {
                Character readYn = ame.getReadYn();
                Character writeYn = ame.getWriteYn();

                item.getData().setReadYn(readYn);
                item.getData().setWriteYn(writeYn);
                item.getData().setCreateId(ame.getCreateId());
                item.getData().setCreateNm(ame.getCreateNm());
                item.getData().setCreateDatetime(ame.getCreateDatetime());
            }
        });

        return allVueMenus;
    }

    // VueMenu Tree 만들기
    public List<VueMenuDto> createVueMenuTree(List<VueMenuDto> menus, List<VueMenuDto> allMenus) {
        for (VueMenuDto vueMenu : menus) {
            List<VueMenuDto> children = allMenus.stream()
                    .filter((m) -> m.getData().getParentMenuId() != 0
                            && Objects.equals(m.getData().getParentMenuId(), vueMenu.getData().getMenuSeq()))
                    .collect(Collectors.toList());

            if (children.size() > 0) {
                createVueMenuTree(children, allMenus);
                vueMenu.setChildren(children);
            } else {
                vueMenu.setChildren(new ArrayList<>());
            }
        }

        return menus;
    }
}
