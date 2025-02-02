package com.gomo.app.quest.exception;

import com.gomo.app.common.exception.DomainException;

public class AssignQuestThresholdExceededException extends DomainException {

	public AssignQuestThresholdExceededException(AssignQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public AssignQuestThresholdExceededException(AssignQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
