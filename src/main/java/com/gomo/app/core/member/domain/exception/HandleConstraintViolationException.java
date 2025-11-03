package com.gomo.app.core.member.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.member.domain.exception.code.HandleErrorCode;

public class HandleConstraintViolationException extends ApplicationException {

	public HandleConstraintViolationException(HandleErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public HandleConstraintViolationException(HandleErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
