package com.gomo.app.core.quest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.domain.exception.code.RepeatQuestErrorCode;

public class RepeatQuestNotFoundException extends ApplicationException {

	public RepeatQuestNotFoundException(RepeatQuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public RepeatQuestNotFoundException(RepeatQuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
