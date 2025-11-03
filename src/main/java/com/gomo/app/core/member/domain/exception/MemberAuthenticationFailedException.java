package com.gomo.app.core.member.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.member.domain.exception.code.MemberErrorCode;

public class MemberAuthenticationFailedException extends ApplicationException {

	public MemberAuthenticationFailedException(MemberErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public MemberAuthenticationFailedException(MemberErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
