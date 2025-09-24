package com.gomo.app.support.auth.application;

import java.util.UUID;

import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LogoutMemberUseCase {

	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "MEMBER_LOGOUT")
	public void logout(UUID memberId) {
		authTokenRepository.deleteRefreshToken(memberId);
	}
}
