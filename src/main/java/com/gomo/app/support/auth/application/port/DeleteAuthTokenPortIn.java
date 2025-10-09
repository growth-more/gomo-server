package com.gomo.app.support.auth.application.port;

import java.util.UUID;

public interface DeleteAuthTokenPortIn {

	/**
	 * Deletes the currently active refresh token for a given principal.
	 *
	 * @param principalId The id of the principal whose token should be revoked.
	 */
	void deleteRefreshToken(UUID principalId);
}
