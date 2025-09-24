package com.gomo.app.support.auth.application;

import java.util.UUID;

import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.exception.code.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class RefreshTokenUseCase {

	private final AuthTokenGenerator authTokenGenerator;
	private final JwtUtil jwtUtil;
	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "REFRESH_TOKEN_UPDATE")
	public AuthTokenResponse refresh(String refreshToken) {
		if (refreshToken == null) {
			throw new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED);
		}
		UUID memberId = UUID.fromString(jwtUtil.extractMemberId(refreshToken));

		String storedRefreshToken = authTokenRepository.getRefreshToken(memberId);
		if (!storedRefreshToken.equals(refreshToken)) {
			throw new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED);
		}

		AuthToken authToken = authTokenGenerator.generate(memberId);
		long refreshTokenExpirationTime = authTokenGenerator.getRefreshTokenExpirationTime(authToken.getRefreshToken());

		return AuthTokenResponse.of(memberId, authToken, refreshTokenExpirationTime);
	}
}
