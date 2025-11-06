package com.gomo.app.core.auth.application.service;

import static com.gomo.app.core.auth.domain.exception.AuthErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.auth.application.port.command.CreatePrincipalCommand;
import com.gomo.app.core.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.core.auth.application.port.in.LoginProcessor;
import com.gomo.app.core.auth.application.port.in.PasswordResetProcessor;
import com.gomo.app.core.auth.application.port.in.SignupProcessor;
import com.gomo.app.core.auth.application.port.out.JwtVerifier;
import com.gomo.app.core.auth.application.port.out.PrincipalCreator;
import com.gomo.app.core.auth.application.port.out.PrincipalLoginProcessor;
import com.gomo.app.core.auth.application.port.out.PrincipalPasswordResetter;
import com.gomo.app.core.auth.domain.exception.AuthenticationFailException;
import com.gomo.app.core.auth.domain.model.AuthToken;
import com.gomo.app.core.member.domain.model.LoginProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class AuthService implements SignupProcessor, LoginProcessor, PasswordResetProcessor {

	private final PrincipalCreator principalCreator;
	private final JwtVerifier jwtVerifier;
	private final PrincipalLoginProcessor principalLoginProcessor;
	private final AuthTokenService authTokenService;
	private final PrincipalPasswordResetter principalPasswordResetter;

	@Override
	@AuditLog(action = "SIGNUP")
	public UUID signup(CreatePrincipalCommand command) {
		// TODO [2025-11-02] jhl221123 : 토큰 내부 이메일이 동일한지 확인하는 jwt 기능이 추가되어야 합니다.
		if (LoginProvider.EMAIL.name().equals(command.loginProvider()) && !jwtVerifier.verify(command.temporaryToken())) {
			throw new AuthenticationFailException(INVALID_VERIFIED_EMAIL_TOKEN);
		}
		return principalCreator.create(command);
	}

	@Override
	@AuditLog(action = "LOGIN")
	public AuthTokenDto login(String email, String password) {
		UUID principalId = principalLoginProcessor.login(email, password);
		AuthToken authToken = authTokenService.create(principalId);
		long expirationTime = jwtVerifier.extractExpirationTime(authToken.getRefreshToken());
		return AuthTokenDto.of(principalId, authToken.getAccessToken(), authToken.getRefreshToken(), expirationTime);
	}

	@Override
	@AuditLog(action = "TOKEN_VERIFY_AND_PASSWORD_RESET")
	public void reset(String email, String newPassword, String temporaryToken) {
		// TODO [2025-11-04] jhl221123 : 토큰 내부 이메일이 동일한지 확인하는 jwt 기능이 추가되어야 합니다.
		if (!jwtVerifier.verify(temporaryToken)) {
			throw new AuthenticationFailException(INVALID_VERIFIED_EMAIL_TOKEN);
		}
		principalPasswordResetter.reset(email, newPassword);
	}
}
