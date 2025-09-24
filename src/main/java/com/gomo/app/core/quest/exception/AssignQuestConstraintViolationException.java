package com.gomo.app.core.quest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;

public class AssignQuestConstraintViolationException extends ApplicationException {

	public AssignQuestConstraintViolationException(AssignQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public AssignQuestConstraintViolationException(AssignQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
