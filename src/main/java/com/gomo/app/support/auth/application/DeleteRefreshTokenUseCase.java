package com.gomo.app.support.auth.application;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.auth.application.port.DeleteAuthTokenPortIn;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class DeleteRefreshTokenUseCase implements DeleteAuthTokenPortIn {

	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "DELETE_REFRESH_TOKEN")
	@Override
	public void deleteRefreshToken(UUID principalId) {
		authTokenRepository.deleteRefreshToken(principalId);
	}
}
