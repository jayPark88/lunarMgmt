package com.lunar.lunarMgmt.api.setting.model;

import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.jpa.entities.CommonGroupCodeEntity;
import com.lunar.lunarMgmt.common.jpa.entities.pk.CommonCodePK;
import com.lunar.lunarMgmt.common.model.AdminBaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class CommonCodeDto extends AdminBaseDto implements ChangableToFromEntity<CommonCodeEntity> {

    private String groupCode;
    private String code;
    private String name;
    private String desc;
    private String codeVal1;
    private String codeVal2;
    private String codeVal3;
    private int sortNum;
    private Character useYn;

    // front에서 사용할 멤버
    @Builder.Default
    public boolean focus = false;

    public CommonCodeDto(CommonCodeEntity entity) {
        this();
        from(entity);
    }

    @Override
    public CommonCodeEntity to() {
        return CommonCodeEntity.builder()
                .commonCodePk(new CommonCodePK(groupCode, code))
                .cdNm(name)
                .cdDesc(desc)
                .cdVal1(codeVal1)
                .cdVal2(codeVal2)
                .cdVal3(codeVal3)
                .sortNum(sortNum)
                .useYn(useYn)
                .commonGroupCode(CommonGroupCodeEntity.builder().grpCd(groupCode).build())
                .build();
    }

    @Override
    public void from(CommonCodeEntity entity) {
        groupCode = entity.getCommonCodePk().getGrpCd();
        code = entity.getCommonCodePk().getCd();
        name = entity.getCdNm();
        desc = entity.getCdDesc();
        codeVal1 = entity.getCdVal1();
        codeVal2 = entity.getCdVal2();
        codeVal3 = entity.getCdVal3();
        sortNum = entity.getSortNum();
        useYn = entity.getUseYn();
    }
}
