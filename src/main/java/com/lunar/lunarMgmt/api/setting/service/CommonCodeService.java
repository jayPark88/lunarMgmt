package com.lunar.lunarMgmt.api.setting.service;

import com.lunar.lunarMgmt.api.setting.abst.SettingCommonCodeAbstract;
import com.lunar.lunarMgmt.api.setting.model.CodeSearchDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeSort;
import com.lunar.lunarMgmt.api.setting.model.CommonGroupCode;
import com.lunar.lunarMgmt.common.model.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeService {

    private final SettingCommonCodeAbstract commonCodeAbstract;

    public List<CommonGroupCode> selectCommonGrpCodeList(CodeSearchDto searchDto, PageRequest pageRequest){
        return commonCodeAbstract.selectCommonGrpCodeList(searchDto, pageRequest);
    }

    public List<CommonCodeDto> selectCommonCodeList(String grpCd){
        return commonCodeAbstract.selectCommonCodeList(grpCd);
    }

    public CommonCodeDto selectCommonCodeInfo(String cd){
        return commonCodeAbstract.selectCommonCodeInfo(cd);
    }

    public void saveGroupCode(CommonGroupCode commonGroupCode){
        commonCodeAbstract.saveGroupCode(commonGroupCode);
    }

    public void saveCommonCode(CommonCodeDto commonCodeDto){
        commonCodeAbstract.saveCommonCode(commonCodeDto);
    }

    public void deleteGroupCode(String grpCd) {
        commonCodeAbstract.deleteGroupCode(grpCd);
    }

    public void deleteCommonCode(String grpCd, String cd) {
        commonCodeAbstract.deleteCommonCode(grpCd, cd);
    }

    public void sortCommonCode(CommonCodeSort commonCodeSort){
        commonCodeAbstract.sortCommonCode(commonCodeSort);
    }
}
