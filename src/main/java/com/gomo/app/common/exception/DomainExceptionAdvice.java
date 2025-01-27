package com.gomo.app.common.exception;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class DomainExceptionAdvice {

	@ExceptionHandler(DomainException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(DomainException e, HttpServletRequest request) {
		log.warn("[Domain exception occurred!] code: {}, message: {}", e.getCode(), e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(
			LocalDateTime.now(),
			e.getHttpStatus(),
			e.getCode(),
			e.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(MaxUploadSizeExceededException e, HttpServletRequest request) {
		log.warn("[File upload exception occurred!] code: {}, message: {}", IMAGE_TOO_LARGE, e.getMessage(), e);
		ErrorResponse errorResponse = ErrorResponse.of(
			LocalDateTime.now(),
			IMAGE_TOO_LARGE.getHttpStatus(),
			IMAGE_TOO_LARGE.name(),
			e.getMessage(),
			request.getRequestURI()
		);

		return ResponseEntity.status(IMAGE_TOO_LARGE.getHttpStatus()).body(errorResponse);
	}
}
