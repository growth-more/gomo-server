package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.MottoErrorCode;

public class MottoConstraintViolationException extends ApplicationException {

	public MottoConstraintViolationException(MottoErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public MottoConstraintViolationException(MottoErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
