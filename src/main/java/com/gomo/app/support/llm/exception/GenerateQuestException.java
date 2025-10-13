package com.gomo.app.support.llm.exception;

import com.gomo.app.common.exception.ApplicationException;

public class GenerateQuestException extends ApplicationException {
	public GenerateQuestException(GenerateQuestErrorCode errorCode){
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public GenerateQuestException(GenerateQuestErrorCode errorCode, Throwable cause){
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
