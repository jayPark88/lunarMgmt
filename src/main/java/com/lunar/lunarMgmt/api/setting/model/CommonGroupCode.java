package com.lunar.lunarMgmt.api.setting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.CommonGroupCodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CommonGroupCode implements ChangableToFromEntity<CommonGroupCodeEntity> {

    private String code;
    private String name;
    private String desc;
    private int sortNum;
    private Character useYn;
    private String createId;
    private String createNm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;
    private String modifyId;
    private String modifyNm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyDatetime;

    private List<CommonCodeDto> commonCodeDtos;

    // front에서 사용할 멤버
    public boolean focus = false;
    public boolean getOpen() {
        return this.commonCodeDtos.size() > 0 ? true : false;
    }

    public CommonGroupCode(CommonGroupCodeEntity entity) {
        commonCodeDtos = new ArrayList<>();
        from(entity);
    }

    public CommonGroupCode commonCodeDtos(List<CommonCodeDto> commonCodeDtoList) {
        this.commonCodeDtos = commonCodeDtoList;
        return this;
    }

    @Override
    public CommonGroupCodeEntity to() {
        return CommonGroupCodeEntity.builder()
                .grpCd(code)
                .grpCdNm(name)
                .grpCdDesc(desc)
                .sortNum(sortNum)
                .useYn(useYn)
                .createId(createId)
                .createNm(createNm)
                .createDatetime(createDatetime)
                .modifyId(modifyId)
                .modifyNm(modifyNm)
                .modifyDatetime(modifyDatetime)
                .build();
    }

    @Override
    public void from(CommonGroupCodeEntity entity) {
        code = entity.getGrpCd();
        name = entity.getGrpCdNm();
        desc = entity.getGrpCdDesc();
        sortNum = entity.getSortNum();
        useYn = entity.getUseYn();
        createId = entity.getCreateId();
        createNm = entity.getCreateNm();
        createDatetime = entity.getCreateDatetime();
        modifyId = entity.getModifyId();
        modifyNm = entity.getModifyNm();
        modifyDatetime = entity.getModifyDatetime();
    }

}
