package com.lunar.lunarMgmt.common.exception;

import com.lunar.lunarMgmt.common.exception.model.ErrorCode;

public class NotFoundTokenException extends UncheckedException {

    private static final long serialVersionUID = 1L;

    public NotFoundTokenException() { super(ErrorCode.NOT_FOUND_TOKEN); }
}
