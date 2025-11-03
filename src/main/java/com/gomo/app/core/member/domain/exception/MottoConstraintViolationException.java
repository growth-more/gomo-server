package com.gomo.app.core.member.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.member.domain.exception.code.MottoErrorCode;

public class MottoConstraintViolationException extends ApplicationException {

	public MottoConstraintViolationException(MottoErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public MottoConstraintViolationException(MottoErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
