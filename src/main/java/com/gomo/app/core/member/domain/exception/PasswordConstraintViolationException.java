package com.gomo.app.core.member.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.member.domain.exception.code.PasswordErrorCode;

public class PasswordConstraintViolationException extends ApplicationException {

	public PasswordConstraintViolationException(PasswordErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public PasswordConstraintViolationException(PasswordErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
