package com.gomo.app.support.auth.domain.repository;

import java.util.UUID;

public interface AuthTokenRepository {
	void setRefreshToken(UUID memberId, String refreshToken);

	String getRefreshToken(UUID memberId);

	void deleteRefreshToken(UUID memberId);
}
