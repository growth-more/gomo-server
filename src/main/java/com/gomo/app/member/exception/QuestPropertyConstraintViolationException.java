package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.QuestPropertyErrorCode;

public class QuestPropertyConstraintViolationException extends ApplicationException {

	public QuestPropertyConstraintViolationException(QuestPropertyErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public QuestPropertyConstraintViolationException(QuestPropertyErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
