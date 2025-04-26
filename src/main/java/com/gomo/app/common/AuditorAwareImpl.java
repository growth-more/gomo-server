package com.gomo.app.common;

import java.util.Optional;

import com.gomo.app.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

	private final ObjectProvider<HttpServletRequest> requestProvider;
	private final JwtUtil jwtUtil;

	@Override
	public Optional<String> getCurrentAuditor() {
		HttpServletRequest request = requestProvider.getIfAvailable();
		if (request == null) {
			return Optional.of("SYSTEM");
		}

		String token = extractTokenFromHeader(request);
		if (!StringUtils.hasText(token) || !jwtUtil.validateToken(token)) {
			return Optional.of("SYSTEM");
		}
		String memberId = jwtUtil.extractMemberId(token);
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
