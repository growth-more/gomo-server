package com.gomo.app.core.interest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.domain.exception.code.ProficiencyErrorCode;

public class ProficiencyAdjustFailureException extends ApplicationException {

	public ProficiencyAdjustFailureException(ProficiencyErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public ProficiencyAdjustFailureException(ProficiencyErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
