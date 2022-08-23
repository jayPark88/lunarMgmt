package com.lunar.lunarMgmt.api.setting.abst.sub;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.abst.SettingCommonCodeAbstract;
import com.lunar.lunarMgmt.api.setting.model.*;
import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.jpa.entities.CommonGroupCodeEntity;
import com.lunar.lunarMgmt.common.jpa.entities.pk.CommonCodePK;
import com.lunar.lunarMgmt.common.jpa.repository.CommonCodeRepository;
import com.lunar.lunarMgmt.common.jpa.repository.CommonGroupCodeRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Transactional
public class SettingCommonCodeSub extends SettingCommonCodeAbstract {

    public SettingCommonCodeSub(CommonCodeRepository commonCodeRepository, CommonGroupCodeRepository commonGroupCodeRepository) {
        super(commonCodeRepository, commonGroupCodeRepository);
    }

    @Override
    public List<CommonGroupCode> selectCommonGrpCodeList(CodeSearchDto searchDto, PageRequest pageRequest) {
        // sort만
        pageRequest.setSort("sortNum");
        pageRequest.setDirection("asc");

        // 공통코드 구하는 검색이면
        if (StringUtils.hasLength(searchDto.getCode()) || StringUtils.hasLength(searchDto.getCodeName())) {
            return searchCommonCodeListWithGroup(searchDto.toCommonCodeSearchDto(null), pageRequest);
        }

        // 그룹코드 구하는 검색이면
        List<CommonGroupCodeEntity> commonGroupCodeEntities = commonGroupCodeRepository.searchGroupCodeList(searchDto.toGroupCodeSearchDto());

        if (commonGroupCodeEntities.isEmpty())
            return new ArrayList<>();
        else
            return commonGroupCodeEntities.stream().map(e -> {
                CommonGroupCode cgc = new CommonGroupCode(e);
                List<CommonCodeDto> commonCodeDtoList =
                        searchCommonCodeList(searchDto.toCommonCodeSearchDto(e.getGrpCd()), pageRequest);

                return cgc.commonCodes(commonCodeDtoList);
            }).collect(Collectors.toList());
    }

    @Override
    public List<CommonCodeDto> selectCommonCodeList(String groupCode) {
        List<CommonCodeEntity> commonCodeEntities = commonCodeRepository.findAllByCommonCodePkGrpCdOrderBySortNum(groupCode);
        List<CommonCodeDto> commonCodes = commonCodeEntities.stream().map(e -> new CommonCodeDto(e)).collect(Collectors.toList());
        return commonCodes;
    }

    @Override
    public CommonCodeDto selectCommonCodeInfo(String code) {
        return new CommonCodeDto(commonCodeRepository.findByCommonCodePkCd(code));
    }

    @Override
    public void saveGroupCode(CommonGroupCode commonGroupCode) {
        commonGroupCodeRepository.save(commonGroupCode.to());
    }

    @Override
    public void saveCommonCode(CommonCodeDto commonCodeDto) {
        commonCodeRepository.save(commonCodeDto.to());
    }

    @Override
    public void deleteGroupCode(String groupCode) {
        commonGroupCodeRepository.deleteById(groupCode);
    }

    @Override
    public void deleteCommonCode(String groupCode, String code) {
        CommonCodeEntity cce = commonCodeRepository.findById(new CommonCodePK(groupCode, code)).get();
        int sortNum = cce.getSortNum();
        commonCodeRepository.deleteById(new CommonCodePK(groupCode, code));

        List<CommonCodeEntity> sortCodes = commonCodeRepository
                .findByCommonCodePkGrpCdAndCommonCodePkCdNotAndSortNumGreaterThan(groupCode, code, sortNum);

        sortCodes = sortCodes.stream().map((sc) -> {
            CommonCodeDto cc = new CommonCodeDto(sc);
            cc.setSortNum(sc.getSortNum() - 1);
            return cc.to();
        }).collect(Collectors.toList());

        commonCodeRepository.saveAll(sortCodes);
    }

    @Override
    public void sortCommonCode(CommonCodeSort commonCodeSort) {
        List<CommonCodeEntity> sortCodes;

        // 같은 부모 메뉴의 하위 메뉴들 정렬 순서 변경
        if (commonCodeSort.getSortNum() < commonCodeSort.getBeforeSortNum()) {
            sortCodes =
                    commonCodeRepository.findByCommonCodePkGrpCdAndCommonCodePkCdNotAndSortNumBetween(commonCodeSort.getGroupCode(),
                            commonCodeSort.getCode(), commonCodeSort.getSortNum(), commonCodeSort.getBeforeSortNum() - 1);

            sortCodes = sortCodes.stream().map((sc) -> {
                CommonCodeDto cc = new CommonCodeDto(sc);
                cc.setSortNum(sc.getSortNum() + 1);
                return cc.to();
            }).collect(Collectors.toList());

        } else {
            sortCodes =
                    commonCodeRepository.findByCommonCodePkGrpCdAndCommonCodePkCdNotAndSortNumBetween(commonCodeSort.getGroupCode(),
                            commonCodeSort.getCode(), commonCodeSort.getBeforeSortNum() + 1, commonCodeSort.getSortNum());

            sortCodes = sortCodes.stream().map((sc) -> {
                CommonCodeDto cc = new CommonCodeDto(sc);
                cc.setSortNum(sc.getSortNum() - 1);
                return cc.to();
            }).collect(Collectors.toList());
        }

        List<CommonCodeEntity> savedList = commonCodeRepository.saveAll(sortCodes);

        // 변경이 되었으면
        if (savedList.size() > 0) {
            // 변경 할 메뉴의 정렬 순서 변경
            CommonCodePK pk = new CommonCodePK(commonCodeSort.getGroupCode(), commonCodeSort.getCode());
            CommonCodeEntity entity = commonCodeRepository.findById(pk).get();
            CommonCodeDto dto = new CommonCodeDto(entity);
            dto.setSortNum(commonCodeSort.getSortNum());

            commonCodeRepository.save(dto.to());
        }
    }


    // 공통 코드 먼저 검색하고 상위 그룹 코드 구하기
    public List<CommonGroupCode> searchCommonCodeListWithGroup(CommonCodeSearchDto searchDto, PageRequest pageRequest) {
        Page<CommonCodeEntity> commonCodeEntities = commonCodeRepository.searchGroupCodeList(searchDto, pageRequest);

        // 공통코드 -> 그룹코드를 구하는 방식으로 그룹코드 - 공통코드 계층구조를 생성한다.
        Map<String, CommonGroupCode> resultMap = new HashMap<>();

        commonCodeEntities.stream().forEach((e) -> {
            CommonCodeDto cc = new CommonCodeDto(e);

            // 그룹코드 Map에 넣기
            CommonGroupCodeEntity cgce = e.getCommonGroupCode();
            CommonGroupCode cgc = null;

            if (!resultMap.containsKey(cgce.getGrpCd())) {
                cgc = new CommonGroupCode(cgce);
                resultMap.put(cgc.getCode(), cgc);

            } else {
                cgc = resultMap.get(cgce.getGrpCd());
            }

            cgc.getCommonCodeDtos().add(cc);
        });
        return new ArrayList<>(resultMap.values());
    }

    // CommonCodeSearchDto로 공통코드 구하기
    public List<CommonCodeDto> searchCommonCodeList(CommonCodeSearchDto searchDto, PageRequest pageRequest) {
        Page<CommonCodeEntity> commonCodeEntities = commonCodeRepository.searchGroupCodeList(searchDto, pageRequest);
        return commonCodeEntities.getContent().stream().map(e -> new CommonCodeDto(e)).collect(Collectors.toList());
    }
}
