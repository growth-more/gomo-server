package com.gomo.app.common.displayorder;

import com.gomo.app.common.exception.ApplicationException;

public class DisplayOrderConstraintViolationException extends ApplicationException {

	public DisplayOrderConstraintViolationException(DisplayOrderErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public DisplayOrderConstraintViolationException(DisplayOrderErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
