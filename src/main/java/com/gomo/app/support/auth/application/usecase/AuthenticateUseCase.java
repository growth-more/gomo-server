package com.gomo.app.support.auth.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.MemberLoginProcessor;
import com.gomo.app.support.auth.application.port.JwtVerifier;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.domain.model.AuthToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class AuthenticateUseCase {

	private final MemberLoginProcessor memberLoginProcessor;
	private final JwtVerifier jwtVerifier;
	private final CreateAuthTokenInternalService createAuthTokenInternalService;

	@AuditLog(action = "AUTHENTICATE_PRINCIPAL")
	public AuthTokenDto authenticate(String email, String password) {
		UUID principalId = memberLoginProcessor.login(email, password);
		AuthToken authToken = createAuthTokenInternalService.create(principalId);
		long expirationTime = jwtVerifier.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenDto.of(principalId, authToken.getAccessToken(), authToken.getRefreshToken(), expirationTime);
	}
}
