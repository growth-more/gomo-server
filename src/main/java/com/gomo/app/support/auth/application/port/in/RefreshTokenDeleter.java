package com.gomo.app.support.auth.application.port.in;

import java.util.UUID;

public interface RefreshTokenDeleter {

	/**
	 * Deletes the currently active refresh token for a given principal.
	 *
	 * @param principalId The id of the principal whose token should be revoked.
	 */
	void delete(UUID principalId);
}
