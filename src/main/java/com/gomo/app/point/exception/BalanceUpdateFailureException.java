package com.gomo.app.point.exception;

import com.gomo.app.common.exception.DomainException;

public class BalanceUpdateFailureException extends DomainException {

	public BalanceUpdateFailureException(PointWalletErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public BalanceUpdateFailureException(PointWalletErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
