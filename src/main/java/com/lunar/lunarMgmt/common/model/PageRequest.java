package com.lunar.lunarMgmt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
  private int page = 1; // default 1
  private int size = 10; // default 10
  
  private String sort;
  private Direction direction;
  
  public void setDirection(String direction) {
    this.direction = Direction.ASC;

    if (StringUtils.hasLength(direction)) {
      if ("DESC".equalsIgnoreCase(direction))
        this.direction = Direction.DESC;
    }
  }

  public org.springframework.data.domain.PageRequest of() {    
    if (StringUtils.hasLength(sort))
      return org.springframework.data.domain.PageRequest.of(page - 1, size, Sort.by(this.direction, sort));
    
    return org.springframework.data.domain.PageRequest.of(page - 1, size);
  }
}
