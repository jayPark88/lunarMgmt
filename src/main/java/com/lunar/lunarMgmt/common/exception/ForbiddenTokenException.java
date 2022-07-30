package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

public class ForbiddenTokenException extends UncheckedException {

  private static final long serialVersionUID = 1L;

  public ForbiddenTokenException() { super(ErrorCode.FORBIDDEN_TOKEN); }
}
