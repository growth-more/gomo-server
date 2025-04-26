package com.gomo.app.point.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.point.exception.code.BalanceErrorCode;

public class InsufficientBalanceException extends ApplicationException {

	public InsufficientBalanceException(BalanceErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InsufficientBalanceException(BalanceErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
