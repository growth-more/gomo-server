package com.gomo.app.support.auth.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.support.auth.application.port.JwtCreator;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class CreateAuthTokenInternalService {

	private final JwtCreator jwtCreator;
	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "CREATE_AUTH_TOKEN")
	public AuthToken create(UUID principalId) {
		String accessToken = jwtCreator.createAccessToken(principalId);
		String refreshToken = jwtCreator.createRefreshToken(principalId);
		authTokenRepository.setRefreshToken(principalId, refreshToken);
		return AuthToken.of(accessToken, refreshToken);
	}
}
