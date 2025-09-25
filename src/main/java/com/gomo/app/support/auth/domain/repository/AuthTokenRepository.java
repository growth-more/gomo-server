package com.gomo.app.support.auth.domain.repository;

import java.util.UUID;

public interface AuthTokenRepository {

	void setRefreshToken(UUID principalId, String refreshToken);

	String getRefreshToken(UUID principalId);

	void deleteRefreshToken(UUID principalId);
}
