package com.gomo.app.member.exception;

import com.gomo.app.common.exception.DomainException;

public class MemberNotFoundException extends DomainException {

	public MemberNotFoundException(MemberErrorCode errorCode, String message) {
		super(errorCode.getHttpStatus(), errorCode.name(), message);

	}

	public MemberNotFoundException(MemberErrorCode errorCode, String message, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), message, cause);
	}
}
