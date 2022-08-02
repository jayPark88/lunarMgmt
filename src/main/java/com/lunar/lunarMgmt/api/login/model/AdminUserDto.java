package com.lunar.lunarMgmt.api.login.model;

import com.lunar.lunarMgmt.common.intf.ChangableToFromEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminAuthEntity;
import com.lunar.lunarMgmt.common.jpa.entities.AdminUserEntity;
import com.lunar.lunarMgmt.common.model.AdminBaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDto extends AdminBaseDto implements ChangableToFromEntity<AdminUserEntity> {

  private Long adminUserSeq;
  private String adminUserId;
  private String adminUserNm;
  private String adminUserPwd;
  private String dept;
  private String position;
  private Long authSeq;
  private Character deleteYn;
  private Character useYn;

  public AdminUserDto(AdminUserEntity entity) {
    from(entity);
  }

  @Override
  public AdminUserEntity to() {
    return AdminUserEntity.builder()
            .adminUserSeq(adminUserSeq)
            .adminUserId(adminUserId)
            .adminUserNm(adminUserNm)
            .adminUserPwd(adminUserPwd)
            .dept(dept)
            .position(position)
            .auth(authSeq != null ? AdminAuthEntity.builder().authSeq(authSeq).build() : null)
            .deleteYn(deleteYn)
            .useYn(useYn)
            .build();
  }

  @Override
  public void from(AdminUserEntity entity) {
    adminUserSeq = entity.getAdminUserSeq();
    adminUserId = entity.getAdminUserId();
    adminUserNm = entity.getAdminUserNm();
    adminUserPwd = entity.getAdminUserPwd();
    dept = entity.getDept();
    position = entity.getPosition();
    deleteYn = entity.getDeleteYn();
    useYn = entity.getUseYn();
    authSeq = entity.getAuth().getAuthSeq();
  }

  public AdminUserDto withoutPasswd() {
    this.adminUserPwd = null;
    return this;
  }
}
