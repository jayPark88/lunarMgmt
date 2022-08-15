package com.lunar.lunarMgmt.api.setting.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lunar.lunarMgmt.api.login.model.AdminUserDto;
import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthMenuEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminMenuEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAuthMenuDto implements ChangableToFromEntity<AdminAuthMenuEntity> {

    private Long authMenuSeq;
    private Long authSeq;
    private Long menuSeq;
    private Character readYn;
    private Character writeYn;
    private String createId;
    private String createNm;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDatetime;

    public AdminAuthMenuDto(AdminAuthMenuEntity adminAuthMenuEntity) {
        from(adminAuthMenuEntity);
    }

    @Override
    public AdminAuthMenuEntity to() {
        return AdminAuthMenuEntity.builder()
                .authMenuSeq(authMenuSeq)
                .auth(AdminAuthEntity.builder().authSeq(authSeq).build())
                .menu(AdminMenuEntity.builder().menuSeq(menuSeq).build())
                .readYn(ObjectUtils.isEmpty(readYn)? 'N' : readYn)
                .writeYn(ObjectUtils.isArray(writeYn)? 'N' : writeYn)
                .createId(createId)
                .createNm(createNm)
                .createDatetime(createDatetime)
                .build();
    }

    @Override
    public void from(AdminAuthMenuEntity entity) {
        authMenuSeq = entity.getAuthMenuSeq();
        authSeq = entity.getAuth().getAuthSeq();
        menuSeq = entity.getMenu().getMenuSeq();
        readYn = entity.getReadYn();
        writeYn = entity.getWriteYn();
        createId = entity.getCreateId();
        createNm = entity.getCreateNm();
        createDatetime = entity.getCreateDatetime();
    }

    public void setCreateInfo(AdminUserDto adminUserDto) {
        this.createId = adminUserDto.getAdminUserId();
        this.createNm = adminUserDto.getAdminUserNm();
        this.createDatetime = LocalDateTime.now();
    }
}
