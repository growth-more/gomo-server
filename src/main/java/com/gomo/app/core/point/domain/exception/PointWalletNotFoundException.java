package com.gomo.app.core.point.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.point.domain.exception.code.PointWalletErrorCode;

public class PointWalletNotFoundException extends ApplicationException {

	public PointWalletNotFoundException(PointWalletErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public PointWalletNotFoundException(PointWalletErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
