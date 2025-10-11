package com.gomo.app.support.auth.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.security.jwt.application.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.port.LoginMemberPortIn;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class AuthenticateUseCase {

	private final LoginMemberPortIn loginMemberPortIn;
	private final VerifyJwtPortIn verifyJwtPortIn;
	private final CreateAuthTokenInternalService createAuthTokenInternalService;

	@AuditLog(action = "AUTHENTICATE_PRINCIPAL")
	public AuthTokenDto authenticate(String email, String password) {
		UUID principalId = loginMemberPortIn.authenticate(email, password);
		AuthToken authToken = createAuthTokenInternalService.create(principalId);
		long expirationTime = verifyJwtPortIn.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenDto.of(principalId, authToken.getAccessToken(), authToken.getRefreshToken(), expirationTime);
	}
}
