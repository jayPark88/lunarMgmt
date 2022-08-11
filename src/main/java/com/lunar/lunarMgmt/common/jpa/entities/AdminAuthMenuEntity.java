package com.lunar.lunarMgmt.common.jpa.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "ADMIN_AUTH_MENU",
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"AUTH_SEQ","MENU_SEQ"}
                )
        }
)
public class AdminAuthMenuEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AUTH_MENU_SEQ")
  private Long authMenuSeq;

  @ManyToOne(fetch = FetchType.LAZY) // AuthMenu를 구할 경우 대부분 Menu에 대한 정보를 가져오기 때문에 auth는 LAZY
  @JoinColumn(name = "AUTH_SEQ", nullable = false)
  private AdminAuthEntity auth;

  @ManyToOne
  @JoinColumn(name = "MENU_SEQ", nullable = false)
  private AdminMenuEntity menu;


  @Column(name = "READ_YN", nullable = false)
  private Character readYn;

  @Column(name = "WRITE_YN", nullable = false)
  private Character writeYn;

  @Column(name = "CREATE_ID", nullable = false)
  private String createId;

  @Column(name = "CREATE_NM", nullable = false)
  private String createNm;

  @Builder.Default
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(name = "CREATE_DATETIME", nullable = false)
  private LocalDateTime createDatetime = LocalDateTime.now();
}
