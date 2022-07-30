package com.lunar.lunarMgmt.common.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name="ADMIN_USER",
        indexes = {
                @Index(name = "index_user_loginId", columnList = "ADMIN_USER_ID"),
                @Index(name = "index_user_userNm", columnList = "ADMIN_USER_NM")
        }
)
public class AdminUserEntity extends AdminBaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ADMIN_USER_SEQ")
  private Long adminUserSeq;

  @Column(name = "ADMIN_USER_ID", columnDefinition = "varchar(50) comment 'HQ-ADMIN 사용자 아이디'")
  private String adminUserId;

  @Column(name = "ADMIN_USER_NM", columnDefinition = "varchar(50) comment 'HQ-ADMIN 사용자 명'")
  private String adminUserNm;

  @Column(name = "ADMIN_USER_PWD" , columnDefinition = "varchar(50) comment 'HQ-ADMIN 사용자 패스워드'")
  private String adminUserPwd;

  @Column(name = "DEPT", columnDefinition = "varchar(50) comment 'HQ-ADMIN 사용자 부서'")
  private String dept;

  @Column(name = "POSITION", columnDefinition = "varchar(50) comment 'HQ-ADMIN 사용자 직급'")
  private String position;

  @ColumnDefault("'N'")
  @Column(name = "DELETE_YN", nullable = false)
  private Character deleteYn;

  @ColumnDefault("'N'")
  @Column(name = "USE_YN", nullable = false)
  private Character useYn;

  @OneToOne
  @JoinColumn(name = "AUTH_SEQ")
  private AdminAuthEntity auth;
}
