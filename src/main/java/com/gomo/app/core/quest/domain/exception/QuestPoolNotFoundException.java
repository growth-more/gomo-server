package com.gomo.app.core.quest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.quest.domain.exception.code.QuestPoolErrorCode;

public class QuestPoolNotFoundException extends ApplicationException {

	public QuestPoolNotFoundException(QuestPoolErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public QuestPoolNotFoundException(QuestPoolErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
