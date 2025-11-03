package com.gomo.app.core.quest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.domain.exception.code.QuestTypeErrorCode;

public class QuestTypeConstraintViolationException extends ApplicationException {

	public QuestTypeConstraintViolationException(QuestTypeErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public QuestTypeConstraintViolationException(QuestTypeErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
