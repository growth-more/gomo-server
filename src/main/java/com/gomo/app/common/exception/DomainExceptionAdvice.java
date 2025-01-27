package com.gomo.app.common.exception;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
