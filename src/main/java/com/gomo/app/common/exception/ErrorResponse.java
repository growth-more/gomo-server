package com.gomo.app.common.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private LocalDateTime timestamp;
	private String path;
	private int httpStatus;
	private String code;
	private String message;

	private ErrorResponse(
		LocalDateTime timestamp,
		String path,
		int httpStatus,
		String code,
		String message
	) {
		this.timestamp = timestamp;
		this.path = path;
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	public static ErrorResponse of(
		LocalDateTime timestamp,
		String path,
		int httpStatus,
		String code,
		String message
	) {
		return new ErrorResponse(timestamp, path, httpStatus, code, message);
	}
}
