package com.gomo.app.support.auth.application;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.jwt.port.GenerateJwtPortIn;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.logging.AuditLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class CreateAuthTokenUseCase {

	private final GenerateJwtPortIn generateJwtPortIn;
	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "CREATE_AUTH_TOKEN")
	public AuthToken create(UUID principalId) {
		String accessToken = generateJwtPortIn.generateAccessToken(principalId);
		String refreshToken = generateJwtPortIn.generateRefreshToken(principalId);
		authTokenRepository.setRefreshToken(principalId, refreshToken);
		return AuthToken.of(accessToken, refreshToken);
	}
}
