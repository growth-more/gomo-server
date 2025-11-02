package com.gomo.app.support.auth.application.port.out;

import java.util.UUID;

public interface JwtCreator {

	String createAccessToken(UUID subject);

	String createRefreshToken(UUID subject);

	/**
	 * Generates a temporary token.
	 *
	 * @param subject token subject
	 * @param expiration time in seconds
	 * @return temporary token
	 */
	String createTemporaryToken(String subject, long expiration);
}
