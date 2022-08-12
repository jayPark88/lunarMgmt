package com.lunar.lunarMgmt.common.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "ADMIN_AUTH")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
@DynamicUpdate
public class AdminAuthEntity extends AdminBaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AUTH_SEQ")
  private Long authSeq;

  @Column(name = "AUTH_CD", unique=true, length = 30)
  private String authCd;

  @Column(name = "AUTH_NM", nullable = false, length = 100)
  private String authNm;

  @Column(name = "AUTH_DESC", length = 255)
  private String authDesc;

  @ColumnDefault("'Y'")
  @Column(name = "USE_YN", nullable = false)
  private Character useYn;

  @Builder.Default
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "auth")
  private List<AdminAuthMenuEntity> adminAuthMenuList = new ArrayList<>();
}
