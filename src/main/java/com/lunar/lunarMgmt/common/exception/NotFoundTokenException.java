package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

import java.io.Serial;

public class NotFoundTokenException extends UncheckedException{

  @Serial
  private static final long serialVersionUID = 1L;

  public NotFoundTokenException() { super(ErrorCode.NOT_FOUND_TOKEN); }
}
