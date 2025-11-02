package com.gomo.app.support.auth.adapter.in.security;

import static com.gomo.app.support.logging.MDC.*;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gomo.app.config.AuthFilterConfiguration;
import com.gomo.app.support.auth.application.port.out.JwtVerifier;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TOKEN = "Bearer ";

	private final AuthFilterConfiguration config;
	private final JwtVerifier jwtVerifier;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();

		//preflight setting
		if (requestMethod.equals("OPTIONS")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (config.isExcluded(requestURI, requestMethod)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = request.getHeader(AUTHORIZATION_HEADER);

		if (token == null || !token.startsWith(BEARER_TOKEN)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Missing or invalid Authorization Header");
			return;
		}
		try {
			token = token.substring(BEARER_TOKEN.length());
			String memberId = jwtVerifier.extractSubject(token);
			MDC.put(MEMBER_ID.name(), memberId);
			log.info("action=MEMBER_AUTHENTICATION, status=success, memberId={}", memberId);
			request.setAttribute("memberId", memberId);
		} catch (JwtException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Invalid or Expired JWT Token");
			return;
		}

		filterChain.doFilter(request, response);
	}
}
