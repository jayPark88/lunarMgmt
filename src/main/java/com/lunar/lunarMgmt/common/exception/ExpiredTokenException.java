package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;
import com.lunar.lunarMgmt.common.exception.UncheckedException;

public class ExpiredTokenException extends UncheckedException {

  private static final long serialVersionUID = 1L;

  public ExpiredTokenException() { super(ErrorCode.EXPIRED_TOKEN); }
}
