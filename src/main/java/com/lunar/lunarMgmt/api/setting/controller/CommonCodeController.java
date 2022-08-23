package com.lunar.lunarMgmt.api.setting.controller;

import com.lunar.lunarMgmt.api.setting.model.CodeSearchDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeDto;
import com.lunar.lunarMgmt.api.setting.model.CommonCodeSort;
import com.lunar.lunarMgmt.api.setting.model.CommonGroupCode;
import com.lunar.lunarMgmt.api.setting.service.CommonCodeService;
import com.lunar.lunarMgmt.common.model.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/setting/code")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping(value="/groups")
    public List<CommonGroupCode> selectCommonGroupCodeList(@ModelAttribute CodeSearchDto searchDto, PageRequest pageRequest){
        return commonCodeService.selectCommonGrpCodeList(searchDto, pageRequest);
    }

    @GetMapping(value = "/groups/{grpCd}")
    public List<CommonCodeDto> selectCommonCodeList(@PathVariable String grpCd){
        return commonCodeService.selectCommonCodeList(grpCd);
    }

    @GetMapping(value = "/codes/{cd}")
    public CommonCodeDto selectCommonCodeInfo(@PathVariable String cd){
        return commonCodeService.selectCommonCodeInfo(cd);
    }

    @PostMapping(value = "/groups")
    public void saveGroupCode(@RequestBody CommonGroupCode commonGroupCode) {
        commonCodeService.saveGroupCode(commonGroupCode);
    }

    @PostMapping(value = "/groups/{grpCd}/codes")
    public void saveCommonCode(@RequestBody CommonCodeDto commonCodeDto) {
        commonCodeService.saveCommonCode(commonCodeDto);
    }

    @PutMapping(value = "/groups")
    public void updateGroupCode(@RequestBody CommonGroupCode commonGroupCode) {
        commonCodeService.saveGroupCode(commonGroupCode);
    }

    @PutMapping(value = "/groups/{grpCd}/codes")
    public void updateCommonCode(@RequestBody CommonCodeDto commonCodeDto) {
        commonCodeService.saveCommonCode(commonCodeDto);
    }

    @DeleteMapping(value = "/groups/{grpCd}")
    public void deleteGroupCode(@PathVariable String grpCd) {
        commonCodeService.deleteGroupCode(grpCd);
    }

    @DeleteMapping(value = "/groups/{grpCd}/codes/{cd}")
    public void deleteCommonCode(@PathVariable String grpCd, @PathVariable String cd) {
        commonCodeService.deleteCommonCode(grpCd, cd);
    }

    @PutMapping(value = "/groups/{grpCd}/codes/sort")
    public void sortCommonCode(@RequestBody CommonCodeSort commonCodeSort){
        commonCodeService.sortCommonCode(commonCodeSort);
    }
}
