package com.gomo.app.quest.exception;

import com.gomo.app.common.exception.DomainException;

public class RepeatQuestAccessDeniedException extends DomainException {

	public RepeatQuestAccessDeniedException(RepeatQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public RepeatQuestAccessDeniedException(RepeatQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
