package com.lunar.lunarMgmt.api.setting.model;

import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.CommonCodeEntity;
import com.lunar.lunarMgmt.common.model.AdminBaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonCode extends AdminBaseDto implements ChangableToFromEntity<CommonCodeEntity> {

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

    public CommonCode(CommonCodeEntity entity) {
        this();
        from(entity);
    }

    @Override
    public CommonCodeEntity to() {
        return null;
    }

    @Override
    public void from(CommonCodeEntity entity) {

    }
}
