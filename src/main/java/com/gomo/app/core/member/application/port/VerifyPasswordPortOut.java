package com.gomo.app.core.member.application.port;

public interface VerifyPasswordPortOut {

	/**
	 * Verifies if a raw password matches a stored, encoded password.
	 *
	 * @param rawPassword The plain-text password submitted for verification.
	 * @param encodedPassword The stored, encoded password from the database to compare against.
	 * @return {@code true} if the raw password matches the encoded password, {@code false} otherwise.
	 */
	boolean matches(String rawPassword, String encodedPassword);
}
