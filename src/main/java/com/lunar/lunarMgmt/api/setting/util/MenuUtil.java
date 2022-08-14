package com.lunar.lunarMgmt.api.setting.util;

import com.lunar.lunarMgmt.api.setting.model.VueMenuDto;
import com.lunar.lunarMgmt.common.jpa.repository.AdminMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MenuUtil {
    private final AdminMenuRepository adminMenuRepository;

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
}
