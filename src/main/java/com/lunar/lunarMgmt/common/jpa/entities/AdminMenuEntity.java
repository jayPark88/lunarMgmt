package com.lunar.lunarMgmt.common.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "ADMIN_MENU")
@AllArgsConstructor
@NoArgsConstructor
public class AdminMenuEntity extends AdminBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MENU_SEQ")
  private Long menuSeq;

  @Column(name = "MENU_NM", nullable = false, length = 100)
  private String menuNm;

  @Column(name = "PAGE_URL", nullable = true, length = 255)
  private String pageUrl;

  @Column(name = "MENU_LEVEL", nullable = false)
  private Integer menuLevel;

  @Column(name = "PARENT_MENU_ID", nullable = false)
  private Long parentMenuId;

  @Column(name = "TOP_MENU_ID", nullable = false)
  private Long topMenuId;

  @Column(name = "MENU_DESC", length = 255)
  private String menuDesc;

  @ColumnDefault("'Y'")
  @Column(name = "USE_YN", nullable = false)
  private Character useYn;

  @Column(name = "SORT_NUM")
  private int sortNum;

  @Setter
  @OneToOne
  @JoinColumn(name = "ON_IMAGE_FILE_SEQ", columnDefinition = "bigint comment '활성화 된 메뉴이미지'")
  private FileEntity onImageFile;

  @Setter
  @OneToOne
  @JoinColumn(name = "OFF_IMAGE_FILE_SEQ", columnDefinition = "bigint comment '비활성화 된 메뉴이미지'")
  private FileEntity offImageFile;

  public void updateTopMenuId(Long topMenuId) {
    this.topMenuId = topMenuId;
  }
}
