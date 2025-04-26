package com.gomo.app.member.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.member.exception.code.MemberNameErrorCode;

public class MemberNameConstraintViolationException extends ApplicationException {

	public MemberNameConstraintViolationException(MemberNameErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public MemberNameConstraintViolationException(MemberNameErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
