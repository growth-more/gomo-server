package com.gomo.app.core.quest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.domain.exception.code.RepeatQuestErrorCode;

public class RepeatQuestThresholdExceededException extends ApplicationException {

	public RepeatQuestThresholdExceededException(RepeatQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public RepeatQuestThresholdExceededException(RepeatQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
