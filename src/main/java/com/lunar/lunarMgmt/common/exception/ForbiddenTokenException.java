package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

import java.io.Serial;

public class ForbiddenTokenException extends UncheckedException {

  @Serial
  private static final long serialVersionUID = 1L;

  public ForbiddenTokenException() { super(ErrorCode.FORBIDDEN_TOKEN); }
}
