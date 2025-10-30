package com.gomo.app.support.auth.infrastructure.persistence;

import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gomo.app.support.auth.application.port.JwtVerifier;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

	private final ObjectProvider<HttpServletRequest> requestProvider;
	private final JwtVerifier jwtVerifier;

	@Override
	public Optional<String> getCurrentAuditor() {
		HttpServletRequest request = requestProvider.getIfAvailable();
		if (request == null) {
			return Optional.of("SYSTEM");
		}

		String token = extractTokenFromHeader(request);
		if (!StringUtils.hasText(token) || !jwtVerifier.verify(token)) {
			return Optional.of("SYSTEM");
		}
		String memberId = jwtVerifier.extractSubject(token);
		return Optional.of(memberId);
	}

	private String extractTokenFromHeader(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
