package com.gomo.app.point.exception;

import com.gomo.app.common.exception.DomainException;

public class InsufficientBalanceException extends DomainException {

	public InsufficientBalanceException(PointWalletErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public InsufficientBalanceException(PointWalletErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
