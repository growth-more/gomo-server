package com.gomo.app.support.auth.application;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class DeleteRefreshTokenUseCase {

	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "DELETE_REFRESH_TOKEN")
	public void delete(UUID principalId) {
		authTokenRepository.deleteRefreshToken(principalId);
	}
}
