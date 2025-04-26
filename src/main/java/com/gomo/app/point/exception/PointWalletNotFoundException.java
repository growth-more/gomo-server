package com.gomo.app.point.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.point.exception.code.PointWalletErrorCode;

public class PointWalletNotFoundException extends ApplicationException {

	public PointWalletNotFoundException(PointWalletErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public PointWalletNotFoundException(PointWalletErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
