package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

import java.io.Serial;

public class ExpiredTokenException extends UncheckedException {

  @Serial
  private static final long serialVersionUID = 1L;

  public ExpiredTokenException() { super(ErrorCode.EXPIRED_TOKEN); }
}
