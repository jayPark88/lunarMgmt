package com.lunar.lunarMgmt.jpa.entity;

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
        name = "ADMIN_USER",
        indexes = {
                @Index(name = "index_user_userNm", columnList = "USER_NM"),
                @Index(name = "index_user_loginId", columnList = "USER_ID")
        })
public class AdminUserEntity extends AdminBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "USER_NM", nullable = false, length = 100)
    private String userNm;

    @Column(name = "USER_ID", unique=true, nullable = false, length = 50)
    private String userId;

    @Column(name = "USER_PASSWD", nullable = false, length = 255)
    private String userPasswd;

    @Column(name = "CELLPHONE_NUM", columnDefinition = "varchar(50) comment '휴대전화'")
    private String cellphoneNum;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "NICKNAME", length = 50)
    private String nickname;

    @ColumnDefault("'Y'")
    @Column(name = "USE_YN", nullable = false)
    private Character useYn;

    @ColumnDefault("'N'")
    @Column(name = "DELETE_YN", nullable = false)
    private Character deleteYn;

    @OneToOne
    @JoinColumn(name = "AUTH_SEQ")
    private AdminAuthEntity auth;

}
