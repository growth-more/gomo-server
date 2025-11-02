package com.gomo.app.core.member.application.port.out;

public interface EmailTokenCreator {

	// TODO [2025-11-02] jhl221123 : 인증 모듈의 책임입니다.

	/**
	 * Creates a temporary, email-based token (JWT) with a specified expiration time.
	 *
	 * @param email      The email address to be embedded as a claim within the token.
	 * @param expiration The duration, in seconds, for which the token will be valid.
	 * @return A {@link String} representing the generated temporary token.
	 */
	String create(String email, long expiration);
}
