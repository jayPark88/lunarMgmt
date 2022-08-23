package com.lunar.lunarMgmt.api;

import com.lunar.lunarMgmt.LunarMgmtApplication;
import com.lunar.lunarMgmt.api.setting.abst.SettingCommonCodeAbstract;
import com.lunar.lunarMgmt.api.setting.model.CodeSearchDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeSort;
import com.lunar.lunarMgmt.api.setting.model.CommonGroupCode;
import com.lunar.lunarMgmt.common.jpa.repository.CommonGroupCodeRepository;
import com.lunar.lunarMgmt.common.model.PageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LunarMgmtApplication.class)
public class AdminCommonCodeTest {

    @Autowired
    SettingCommonCodeAbstract settingCommonCodeAbstract;

    @Autowired
    CommonGroupCodeRepository commonGroupCodeRepository;


    @Test
    public void searchCommonCodeList(){
        // given
        CodeSearchDto codeSearchDto = CodeSearchDto.builder().code("TEST_CD_01").build();
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(10);
        pageRequest.setSort("sortNum");
        pageRequest.setDirection("asc");

        // when
        List<CommonGroupCode> commonGroupCodes = settingCommonCodeAbstract.selectCommonGrpCodeList(codeSearchDto, pageRequest);

        // then
        assertAll(
                () -> assertTrue(commonGroupCodes.stream().filter(item -> item.getCommonCodeDtos().stream().filter(subItem -> subItem.getCode().equals("TEST_CD_01")).findFirst().isPresent()).findFirst().isPresent())
        );
    }

    @Test
    public void selectCommonCodeList(){
        // given
        String groupCode = "TEST_GRP";

        // when
        List<CommonCodeDto> commonCodeDtoList = settingCommonCodeAbstract.selectCommonCodeList(groupCode);

        // then
        assertAll(
                () -> assertTrue(commonCodeDtoList.stream().filter(item -> item.getGroupCode().equals("TEST_GRP")).findFirst().isPresent())
        );
    }

    @Test
    public void saveGroupCode(){
        // given
        CommonGroupCode commonGroupCode =
                CommonGroupCode.builder()
                        .code("TEST_GRP")
                        .name("테스트 그룹 코드")
                        .desc("테스트 그룹 코드 설명")
                        .sortNum(2)
                        .useYn('Y')
                        .createId("jayPark")
                        .createDatetime(LocalDateTime.now())
                        .createNm("박지훈")
                        .modifyId("jayPark")
                        .modifyDatetime(LocalDateTime.now())
                        .modifyNm("박지훈").build();
        // when
        settingCommonCodeAbstract.saveGroupCode(commonGroupCode);

        // then
        assertAll(
                () -> assertTrue(commonGroupCodeRepository.findAll().stream().filter(item -> item.getGrpCd().equals("TEST_GRP")).findFirst().isPresent())
        );
    }

    @Test
    public void selectCommonCodeInfo(){
        // given
        String code = "TEST_CD_01";

        // when
        CommonCodeDto commonCodeDto = settingCommonCodeAbstract.selectCommonCodeInfo(code);

        // then
        assertAll(
                () -> assertTrue(commonCodeDto.getCode().equals(code))
        );
    }

    @Test
    public void saveCommonCode(){
        // given
        CommonCodeDto commonCode =
                CommonCodeDto.builder()
                        .groupCode("TEST_GRP")
                        .code("TEST_CD_02")
                        .name("테스트2")
                        .desc("테스트2")
                        .sortNum(2)
                        .useYn('Y').build();

        // when
        settingCommonCodeAbstract.saveCommonCode(commonCode);

        // then
        assertAll(
                () -> assertTrue(settingCommonCodeAbstract.selectCommonCodeList(commonCode.getGroupCode()).stream().filter(item->item.getGroupCode().equals(commonCode.getGroupCode())).filter(item -> item.getCode().equals("TEST_CD_02")).findFirst().isPresent())
        );
    }

    @Test
    public void deleteCommonGroupCode(){
        // given
        String groupCode = "TEST_GRP3";

        // when
        settingCommonCodeAbstract.deleteGroupCode(groupCode);

        assertAll(
                () ->assertFalse(commonGroupCodeRepository.findAll().stream().filter(item -> item.getGrpCd().equals(groupCode)).findFirst().isPresent())
        );
    }

    @Test
    public void deleteCommonCode(){
        // given
        String groupCode = "TEST_GRP";
        String code = "TEST_CD_02";

        // when
        settingCommonCodeAbstract.deleteCommonCode(groupCode, code);

        // then
        assertAll(
                () -> assertFalse(settingCommonCodeAbstract.selectCommonCodeList(groupCode).stream()
                        .filter(item -> item.getGroupCode().equals(groupCode))
                        .filter(item -> item.getCode().equals(code)).findFirst().isPresent()));
    }

    @Test
    public void sortCommonCode(){
        // given
        CommonCodeSort commonCodeSort = new CommonCodeSort();
        commonCodeSort.setGroupCode("TEST_GRP");
        commonCodeSort.setCode("TEST_CD_02");
        commonCodeSort.setBeforeSortNum(2);
        commonCodeSort.setSortNum(1);

        // when
        settingCommonCodeAbstract.sortCommonCode(commonCodeSort);

        // then
        assertAll(
                () -> assertTrue(settingCommonCodeAbstract.selectCommonCodeList(commonCodeSort.getGroupCode()).stream()
                        .filter(item -> item.getCode().equals(commonCodeSort.getCode()))
                        .filter(item -> item.getGroupCode().equals(commonCodeSort.getGroupCode()))
                        .filter(item -> item.getSortNum() == 1).findFirst().isPresent()
                )
        );

    }
}
