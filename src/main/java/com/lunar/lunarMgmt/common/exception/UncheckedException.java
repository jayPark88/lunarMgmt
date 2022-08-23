package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;
import com.lunar.lunarMgmt.common.exception.model.extend.intf.CreatableErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

public class UncheckedException extends RuntimeException implements CreatableErrorCode {

  @Serial
  private static final long serialVersionUID = 1L;

  private ErrorCode errorCode;

  @Getter
  @Setter
  private String message;

  public UncheckedException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
  public UncheckedException(String message) {
    super(message);
    this.message = message;
  }

  @Override
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
