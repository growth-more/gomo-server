package com.gomo.app.quest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;

public class AssignQuestAccessDeniedException extends ApplicationException {

	public AssignQuestAccessDeniedException(AssignQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public AssignQuestAccessDeniedException(AssignQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
