package com.gomo.app.core.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.member.exception.code.EmailErrorCode;

public class EmailConstraintViolationException extends ApplicationException {

	public EmailConstraintViolationException(EmailErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public EmailConstraintViolationException(EmailErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
