package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.PasswordErrorCode;

public class PasswordMismatchException extends ApplicationException {

	public PasswordMismatchException(PasswordErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public PasswordMismatchException(PasswordErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
