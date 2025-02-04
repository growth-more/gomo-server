package com.gomo.app.quest.exception;

import com.gomo.app.common.exception.DomainException;

public class RepeatQuestThresholdExceededException extends DomainException {

	public RepeatQuestThresholdExceededException(RepeatQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public RepeatQuestThresholdExceededException(RepeatQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
