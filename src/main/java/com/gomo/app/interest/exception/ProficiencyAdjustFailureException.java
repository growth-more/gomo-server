package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.interest.exception.code.ProficiencyErrorCode;

public class ProficiencyAdjustFailureException extends ApplicationException {

	public ProficiencyAdjustFailureException(ProficiencyErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public ProficiencyAdjustFailureException(ProficiencyErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
