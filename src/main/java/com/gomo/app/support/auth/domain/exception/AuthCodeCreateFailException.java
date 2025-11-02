package com.gomo.app.support.auth.domain.exception;

import com.gomo.app.common.exception.ApplicationException;

public class AuthCodeCreateFailException extends ApplicationException {

	public AuthCodeCreateFailException(AuthErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public AuthCodeCreateFailException(AuthErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
