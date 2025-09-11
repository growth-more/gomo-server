package com.gomo.app.logging;

import static com.gomo.app.logging.MDC.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.slf4j.MDC;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest)servletRequest);
			ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse)servletResponse);
			try {
				MDC.put(REQUEST_ID.name(), String.valueOf(UUID.randomUUID()));
				filterChain.doFilter(wrappedRequest, wrappedResponse);
			} finally {
				loggingRequest(wrappedRequest);
				loggingResponse(wrappedResponse);
				wrappedResponse.copyBodyToResponse();
				MDC.clear();
			}
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	private void loggingRequest(ContentCachingRequestWrapper wrappedRequest) {
		try {
			String url = wrappedRequest.getRequestURI();
			String method = wrappedRequest.getMethod();
			String userAgent = wrappedRequest.getHeader("User-Agent");
			if (userAgent == null || userAgent.isBlank()) {
				userAgent = "Unknown";
			}
			String clientIp = getClientIp(wrappedRequest);
			String body = new String(wrappedRequest.getContentAsByteArray(), wrappedRequest.getCharacterEncoding());
			if (body.isBlank()) {
				body = "[None]";
			}
			log.trace("[request] url={}, method={}, clientIp={}, userAgent={}, body={}", url, method, clientIp, userAgent, body);
		} catch (UnsupportedEncodingException e) {
			log.warn("Failed to read request body", e);
		}
	}

	private void loggingResponse(ContentCachingResponseWrapper wrappedResponse) {
		try {
			int status = wrappedResponse.getStatus();
			String body = new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());
			if (body.isBlank()) {
				body = "[None]";
			}
			log.trace("[response] status={}, body={}", status, body);
		} catch (UnsupportedEncodingException e) {
			log.warn("Failed to read response body", e);
		}
	}

	private String getClientIp(HttpServletRequest request) {
		String ip = Stream.of(
				request.getHeader("X-Forwarded-For"),
				request.getHeader("Proxy-Client-IP"),
				request.getHeader("WL-Proxy-Client-IP"),
				request.getHeader("HTTP_CLIENT_IP"),
				request.getHeader("HTTP_X_FORWARDED_FOR"),
				request.getRemoteAddr()
			)
			.map(s -> Objects.toString(s, "").trim())
			.filter(s -> !s.isBlank() && !"unknown".equalsIgnoreCase(s))
			.findFirst()
			.orElse("unknown");

		if (ip.contains(",")) {
			ip = ip.split(",")[0].trim();
		}

		return ip;
	}
}
