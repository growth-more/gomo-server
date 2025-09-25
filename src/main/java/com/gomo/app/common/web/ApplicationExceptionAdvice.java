package com.gomo.app.common.web;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.common.exception.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionAdvice {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e,
		HttpServletRequest request) {
		log.warn("[Application exception] errorCode: {}, message: {}", e.getErrorCode(), e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(
			LocalDateTime.now(),
			request.getRequestURI(),
			e.getHttpStatus(),
			e.getErrorCode(),
			e.getMessage()
		);

		return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
		log.warn("[Internal server error] message: {}", e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(
			LocalDateTime.now(),
			request.getRequestURI(),
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			"Server internal error",
			e.getMessage()
		);

		return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleMultipartException(MaxUploadSizeExceededException e,
		HttpServletRequest request) {
		log.warn("[File upload exception] errorCode: {}, message: {}", "IMA-ROO-001", e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(
			LocalDateTime.now(),
			request.getRequestURI(),
			HttpStatus.PAYLOAD_TOO_LARGE.value(),
			"IMA-ROO-001",
			e.getMessage()
		);

		return ResponseEntity.status(errorResponse.getHttpStatus()).body(errorResponse);
	}
}
