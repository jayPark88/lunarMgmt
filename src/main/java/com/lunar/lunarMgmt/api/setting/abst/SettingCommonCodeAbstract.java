package com.lunar.lunarMgmt.api.setting.abst;

import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.api.setting.model.CodeSearchDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeSort;
import com.lunar.lunarMgmt.api.setting.model.CommonGroupCode;
import com.lunar.lunarMgmt.common.jpa.repository.CommonCodeRepository;
import com.lunar.lunarMgmt.common.jpa.repository.CommonGroupCodeRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class SettingCommonCodeAbstract {
    protected final CommonCodeRepository commonCodeRepository;
    protected final CommonGroupCodeRepository commonGroupCodeRepository;

    // 그룹코드 리스트 조회
    public abstract List<CommonGroupCode> selectCommonGrpCodeList(CodeSearchDto searchDto, PageRequest pageRequest);

    // 그룹코드 공통코드 구하기
    public abstract List<CommonCodeDto> selectCommonCodeList(String groupCode);

    // 공통 코드 상세 정보 조회
    public abstract CommonCodeDto selectCommonCodeInfo(String code);

    // 그룹코드 신규 생성
    public abstract void saveGroupCode(CommonGroupCode commonGroupCode);

    // 공통 코드 신규 생성
    public abstract void saveCommonCode(CommonCodeDto commonCodeDto);

    // 그룹 코드 삭제
    public abstract void deleteGroupCode(String groupCode);

    // 공통 코드 삭제
    public abstract void deleteCommonCode(String groupCode, String code);

    // 공통 코드 정렬 순서 변경
    public abstract void sortCommonCode(CommonCodeSort commonCodeSort);

}
