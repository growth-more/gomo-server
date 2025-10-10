package com.gomo.app.core.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.member.exception.code.HandleErrorCode;

public class HandleDuplicatedException extends ApplicationException {

	public HandleDuplicatedException(HandleErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public HandleDuplicatedException(HandleErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
