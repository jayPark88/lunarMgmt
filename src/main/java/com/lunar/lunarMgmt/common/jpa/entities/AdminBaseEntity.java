package com.lunar.lunarMgmt.common.jpa.entities;

import com.lunar.lunarMgmt.common.jpa.listener.AdminBaseEntityListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners({ AuditingEntityListener.class, AdminBaseEntityListener.class })
public class AdminBaseEntity {

  @CreatedDate
  @Column(name = "CREATE_DATETIME")
  private LocalDateTime createDatetime;

  @Column(name = "CREATE_ID", length = 50, updatable = false)
  private String createId;

  @Column(name = "CREATE_NM", length = 100, updatable = false)
  private String createNm;

  @LastModifiedDate
  @Column(name = "MODIFY_DATETIME")
  private LocalDateTime modifyDatetime;

  @Column(name = "MODIFY_ID", length = 50)
  private String modifyId;

  @Column(name = "MODIFY_NM", length = 100)
  private String modifyNm;
}
