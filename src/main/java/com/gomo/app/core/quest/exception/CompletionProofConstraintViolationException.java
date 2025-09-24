package com.gomo.app.core.quest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.exception.code.CompletionProofErrorCode;

public class CompletionProofConstraintViolationException extends ApplicationException {

	public CompletionProofConstraintViolationException(CompletionProofErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public CompletionProofConstraintViolationException(CompletionProofErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
