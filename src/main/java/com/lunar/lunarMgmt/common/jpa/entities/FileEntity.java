package com.lunar.lunarMgmt.common.jpa.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Setter
@Getter
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FILE")
public class FileEntity extends AdminBaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "FILE_SEQ")
  private Long fileSeq;

  @Column(name = "ORIGIN_FILE_NM", columnDefinition = "varchar(100) comment '원본 파일 이름'")
  private String originFileNm;

  @Column(name = "FILE_NM", columnDefinition = "varchar(100) comment '파일 이름'")
  private String fileNm;

  @Column(name = "FILE_PATH", columnDefinition = "varchar(255) comment '파일 경로'")
  private String filePath;

  @Column(name = "FILE_URI", columnDefinition = "varchar(255) comment '파일 URI'")
  private String fileUri;

  @Column(name = "FILE_EXTEND", columnDefinition = "varchar(50) comment '파일 확장자'")
  private String fileExtend;

  @Column(name = "FILE_SIZE", columnDefinition = "bigint comment '파일 사이즈'")
  private Long fileSize;

  @Column(name = "DEL_YN", columnDefinition = "char(1) comment '삭제 여부'")
  private Character deleteYn;

  @PrePersist
  public void prePersist() {
    this.deleteYn = this.deleteYn == null ? 'N' : this.deleteYn;
  }

  public void updateDeleteYn(Character deleteYn) {
    this.deleteYn = deleteYn;
  }

}
