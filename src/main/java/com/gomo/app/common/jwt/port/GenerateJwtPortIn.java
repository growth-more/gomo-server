package com.gomo.app.common.jwt.port;

import java.util.UUID;

public interface GenerateJwtPortIn {

	String generateAccessToken(UUID subject);

	String generateRefreshToken(UUID subject);

	/**
	 * Generates a temporary token.
	 *
	 * @param subject token subject
	 * @param expiration time in seconds
	 * @return temporary token
	 */
	String generateTemporaryToken(String subject, long expiration);
}
