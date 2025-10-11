package com.gomo.app.support.auth.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.security.jwt.application.port.VerifyJwtPortIn;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.auth.exception.AuthErrorCode;
import com.gomo.app.support.auth.exception.AuthenticationFailException;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class UpdateRefreshTokenUseCase {

	private final CreateAuthTokenInternalService createAuthTokenInternalService;
	private final VerifyJwtPortIn verifyJwtPortIn;
	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "REFRESH_TOKEN_UPDATE")
	public AuthTokenDto update(String refreshToken) {
		if (refreshToken == null) {
			throw new AuthenticationFailException(AuthErrorCode.MISSING_REFRESH_TOKEN);
		}

		UUID principalId = UUID.fromString(verifyJwtPortIn.extractSubject(refreshToken));
		String originRefreshToken = authTokenRepository.getRefreshToken(principalId);
		if (!originRefreshToken.equals(refreshToken)) {
			throw new AuthenticationFailException(AuthErrorCode.INVALID_REFRESH_TOKEN);
		}

		AuthToken authToken = createAuthTokenInternalService.create(principalId);
		long refreshTokenExpirationTime = verifyJwtPortIn.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenDto.of(principalId, authToken.getAccessToken(), authToken.getRefreshToken(), refreshTokenExpirationTime);
	}
}
