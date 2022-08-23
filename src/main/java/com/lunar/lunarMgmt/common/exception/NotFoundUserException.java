package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

import java.io.Serial;

public class NotFoundUserException extends UncheckedException{

  @Serial
  private static final long serialVersionUID = 1L;

  public NotFoundUserException() { super(ErrorCode.NOT_FOUND_USER); }
  public NotFoundUserException(String message) {
    this();
    setMessage(message);
  }
}
