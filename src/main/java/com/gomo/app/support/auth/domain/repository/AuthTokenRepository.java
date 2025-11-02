package com.gomo.app.support.auth.domain.repository;

import java.util.UUID;

public interface AuthTokenRepository {

	void saveRefreshToken(UUID principalId, String refreshToken);

	String findRefreshToken(UUID principalId);

	void deleteRefreshToken(UUID principalId);
}
