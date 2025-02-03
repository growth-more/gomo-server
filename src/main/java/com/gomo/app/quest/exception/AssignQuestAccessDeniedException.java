package com.gomo.app.quest.exception;

import com.gomo.app.common.exception.DomainException;

public class AssignQuestAccessDeniedException extends DomainException {

	public AssignQuestAccessDeniedException(AssignQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public AssignQuestAccessDeniedException(AssignQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
