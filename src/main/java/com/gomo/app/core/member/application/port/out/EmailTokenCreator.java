package com.gomo.app.core.member.application.port.out;

public interface EmailTokenCreator {

	/**
	 * Creates a temporary, email-based token (JWT) with a specified expiration time.
	 *
	 * @param email      The email address to be embedded as a claim within the token.
	 * @param expiration The duration, in seconds, for which the token will be valid.
	 * @return A {@link String} representing the generated temporary token.
	 */
	String create(String email, long expiration);
}
