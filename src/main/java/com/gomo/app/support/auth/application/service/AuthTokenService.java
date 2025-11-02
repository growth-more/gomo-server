package com.gomo.app.support.auth.application.service;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.application.port.in.RefreshTokenDeleter;
import com.gomo.app.support.auth.application.port.in.RefreshTokenUpdater;
import com.gomo.app.support.auth.application.port.out.JwtCreator;
import com.gomo.app.support.auth.application.port.out.JwtVerifier;
import com.gomo.app.support.auth.domain.exception.AuthErrorCode;
import com.gomo.app.support.auth.domain.exception.AuthenticationFailException;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class AuthTokenService implements RefreshTokenDeleter, RefreshTokenUpdater {

	private final JwtCreator jwtCreator;
	private final JwtVerifier jwtVerifier;
	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "AUTH_TOKEN_CREATE")
	public AuthToken create(UUID principalId) {
		String accessToken = jwtCreator.createAccessToken(principalId);
		String refreshToken = jwtCreator.createRefreshToken(principalId);
		authTokenRepository.saveRefreshToken(principalId, refreshToken);
		return AuthToken.of(accessToken, refreshToken);
	}

	@Override
	@AuditLog(action = "REFRESH_TOKEN_UPDATE")
	public AuthTokenDto update(String refreshToken) {
		if (refreshToken == null) {
			throw new AuthenticationFailException(AuthErrorCode.MISSING_REFRESH_TOKEN);
		}

		UUID principalId = UUID.fromString(jwtVerifier.extractSubject(refreshToken));
		String originRefreshToken = authTokenRepository.findRefreshToken(principalId);
		if (!originRefreshToken.equals(refreshToken)) {
			throw new AuthenticationFailException(AuthErrorCode.INVALID_REFRESH_TOKEN);
		}

		AuthToken authToken = create(principalId);
		long refreshTokenExpirationTime = jwtVerifier.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenDto.of(principalId, authToken.getAccessToken(), authToken.getRefreshToken(), refreshTokenExpirationTime);
	}

	@Override
	@AuditLog(action = "REFRESH_TOKEN_DELETE")
	public void delete(UUID principalId) {
		authTokenRepository.deleteRefreshToken(principalId);
	}
}
