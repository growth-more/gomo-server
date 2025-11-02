package com.gomo.app.support.auth.application.service;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.application.port.in.LoginProcessor;
import com.gomo.app.support.auth.application.port.out.JwtVerifier;
import com.gomo.app.support.auth.application.port.out.PrincipalLoginProcessor;
import com.gomo.app.support.auth.domain.model.AuthToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class AuthService implements LoginProcessor {

	private final PrincipalLoginProcessor principalLoginProcessor;
	private final AuthTokenService authTokenService;
	private final JwtVerifier jwtVerifier;

	@Override
	@AuditLog(action = "LOGIN")
	public AuthTokenDto login(String email, String password) {
		UUID principalId = principalLoginProcessor.login(email, password);
		AuthToken authToken = authTokenService.create(principalId);
		long expirationTime = jwtVerifier.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenDto.of(principalId, authToken.getAccessToken(), authToken.getRefreshToken(), expirationTime);
	}
}
