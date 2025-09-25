package com.gomo.app.support.auth.exception;

import com.gomo.app.common.exception.ApplicationException;

public class InvalidAuthCodeException extends ApplicationException {

	public InvalidAuthCodeException(AuthErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InvalidAuthCodeException(AuthErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
