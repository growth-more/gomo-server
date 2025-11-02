package com.gomo.app.support.auth.domain.exception;

import com.gomo.app.common.exception.ApplicationException;

public class AuthenticationFailException extends ApplicationException {

	public AuthenticationFailException(AuthErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public AuthenticationFailException(AuthErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
