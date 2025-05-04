package com.gomo.app.auth.application;

import java.util.UUID;

import com.gomo.app.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.common.ApplicationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LogoutMemberUseCase {

	private final AuthTokenRepository authTokenRepository;

	public void logout(UUID memberId) {
		authTokenRepository.deleteRefreshToken(memberId);
	}
}
