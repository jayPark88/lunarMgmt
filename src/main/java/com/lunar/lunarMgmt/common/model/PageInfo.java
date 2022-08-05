package com.lunar.lunarMgmt.common.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PageInfo {
  private int page;
  private int size;
  private int totalPages;
  private long totalElements;
  private int numberOfElements;
}
