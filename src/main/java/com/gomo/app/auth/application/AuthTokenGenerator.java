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
public class AuthTokenGenerator {

	private final JwtUtil jwtUtil;
	private final AuthTokenRepository authTokenRepository;

	@Transactional
	public AuthToken generate(UUID memberId) {
		String accessToken = jwtUtil.generateAccessToken(memberId);
		String refreshToken = jwtUtil.generateRefreshToken(memberId);
		authTokenRepository.setRefreshToken(memberId, refreshToken);

		return AuthToken.of(accessToken, refreshToken);
	}

	public long getRefreshTokenExpirationTime(String refreshToken) {
		return jwtUtil.extractExpirationTime(refreshToken);
	}
}
