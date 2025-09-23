package com.gomo.app.image.exception;

import com.gomo.app.common.exception.ApplicationException;

public class ImageProcessingException extends ApplicationException {

	public ImageProcessingException(ImageErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public ImageProcessingException(ImageErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
