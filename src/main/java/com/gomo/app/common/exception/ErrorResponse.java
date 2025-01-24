package com.gomo.app.common.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private LocalDateTime timestamp;
	private int httpStatus;
	private String code;
	private String message;
	private String path;

	private ErrorResponse(
		LocalDateTime timestamp,
		int httpStatus,
		String code,
		String message,
		String path
	) {
		this.timestamp = timestamp;
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
		this.path = path;
	}

	public static ErrorResponse of(
		LocalDateTime timestamp,
		int httpStatus,
		String code,
		String message,
		String path
	) {
		return new ErrorResponse(timestamp, httpStatus, code, message, path);
	}
}
