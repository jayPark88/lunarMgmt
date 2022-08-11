package com.lunar.lunarMgmt.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdminBaseDto {

  private String createId;
  private String createNm;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDatetime;
  private String modifyId;
  private String modifyNm;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime modifyDatetime;
}
