package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

public class NotFoundUserException extends UncheckedException{

  private static final long serialVersionUID = 1L;

  public NotFoundUserException() { super(ErrorCode.NOT_FOUND_USER); }
  public NotFoundUserException(String message) {
    this();
    setMessage(message);
  }
}
