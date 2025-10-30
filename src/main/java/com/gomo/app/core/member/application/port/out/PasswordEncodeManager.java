package com.gomo.app.core.member.application.port.out;

public interface PasswordEncodeManager {

	/**
	 * Encodes a given plain-text password.
	 *
	 * @param rawPassword The plain-text password to be encoded.
	 * @return The resulting encoded (hashed and salted) password as a string.
	 */
	String encode(String rawPassword);

	/**
	 * Verifies if a raw password matches a stored, encoded password.
	 *
	 * @param rawPassword The plain-text password submitted for verification.
	 * @param encodedPassword The stored, encoded password from the database to compare against.
	 * @return {@code true} if the raw password matches the encoded password, {@code false} otherwise.
	 */
	boolean verify(String rawPassword, String encodedPassword);
}
