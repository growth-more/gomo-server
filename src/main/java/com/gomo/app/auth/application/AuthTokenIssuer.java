package com.gomo.app.auth.application;

import java.util.UUID;

import com.gomo.app.auth.domain.model.AuthToken;
import com.gomo.app.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class AuthTokenIssuer {

	private final JwtUtil jwtUtil;
	private final AuthTokenRepository authTokenRepository;

	@Transactional
	public AuthToken issue(UUID issuerId) {
		String accessToken = jwtUtil.generateAccessToken(issuerId);
		String refreshToken = jwtUtil.generateRefreshToken(issuerId);
		authTokenRepository.setRefreshToken(issuerId, refreshToken);

		return AuthToken.of(accessToken, refreshToken);
	}

	public long getRefreshTokenExpirationTime(String refreshToken) {
		return jwtUtil.extractExpirationTime(refreshToken);
	}
}
