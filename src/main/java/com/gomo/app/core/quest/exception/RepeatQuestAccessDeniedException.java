package com.gomo.app.core.quest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.exception.code.RepeatQuestErrorCode;

public class RepeatQuestAccessDeniedException extends ApplicationException {

	public RepeatQuestAccessDeniedException(RepeatQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public RepeatQuestAccessDeniedException(RepeatQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
