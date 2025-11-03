package com.gomo.app.support.llm.exception;

import com.gomo.app.common.exception.ApplicationException;

public class LlmException extends ApplicationException {
	public LlmException(LlmErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public LlmException(LlmErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
