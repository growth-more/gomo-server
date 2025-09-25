package com.gomo.app.support.auth.application;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.port.LoginMemberPortIn;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class AuthPrincipalUseCase {

	private final LoginMemberPortIn loginMemberPortIn;
	private final VerifyJwtPortIn verifyJwtPortIn;
	private final CreateAuthTokenUseCase createAuthTokenUseCase;

	@AuditLog(action = "AUTHENTICATE_PRINCIPAL")
	public AuthTokenResponse authenticate(String email, String password) {
		UUID principalId = loginMemberPortIn.authenticate(email, password);
		AuthToken authToken = createAuthTokenUseCase.create(principalId);
		long expirationTime = verifyJwtPortIn.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenResponse.of(principalId, authToken, expirationTime);
	}
}
