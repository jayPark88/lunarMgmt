package com.lunar.lunarMgmt.api.setting.model;

import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.model.AdminBaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper=false)
@Setter
@Getter
@SuperBuilder//부모 클래스에도 같이 선언을 해줘야 한다.. 부모 클래스 필드도 builder하고 싶으면
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto extends AdminBaseDto implements ChangableToFromEntity<AdminAuthEntity> {

    private Long authSeq;
    private String authCd;
    private String authNm;
    private String authDesc;
    private Character useYn;
    public AuthDto(AdminAuthEntity entity) {
        from(entity);
    }
    @Override
    public AdminAuthEntity to() {
        return AdminAuthEntity.builder()
                .authSeq(authSeq)
                .authCd(authCd)
                .authNm(authNm)
                .authDesc(authDesc)
                .useYn(useYn)
                .createId(super.getCreateId())
                .createNm(super.getCreateNm())
                .createDatetime(super.getCreateDatetime())
                .modifyId(super.getModifyId())
                .modifyNm(super.getModifyNm())
                .modifyDatetime(super.getModifyDatetime())
                .build();
    }

    @Override
    public void from(AdminAuthEntity entity) {
        authSeq = entity.getAuthSeq();
        authCd = entity.getAuthCd();
        authNm = entity.getAuthNm();
        authDesc = entity.getAuthDesc();
        useYn = entity.getUseYn();
        super.setCreateId(entity.getCreateId());
        super.setCreateNm(entity.getCreateNm());
        super.setCreateDatetime(entity.getCreateDatetime());
        super.setModifyId(entity.getModifyId());
        super.setModifyNm(entity.getModifyNm());
        super.setModifyDatetime(entity.getModifyDatetime());
    }
}