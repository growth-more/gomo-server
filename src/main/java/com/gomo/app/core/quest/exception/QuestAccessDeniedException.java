package com.gomo.app.core.quest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.exception.code.QuestErrorCode;

public class QuestAccessDeniedException extends ApplicationException {

	public QuestAccessDeniedException(QuestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public QuestAccessDeniedException(QuestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
