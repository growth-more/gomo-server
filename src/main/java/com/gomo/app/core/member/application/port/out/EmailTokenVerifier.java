package com.gomo.app.core.member.application.port.out;

public interface EmailTokenVerifier {

	// TODO [2025-11-02] jhl221123 : 인증 모듈의 책임입니다.

	/**
	 * Verifies the validity of a temporary token, typically used for processes like email verification.
	 *
	 * @param temporaryToken The temporary token string to be validated.
	 * @throws IllegalArgumentException if the token is invalid, expired, or malformed.
	 */
	void verify(String temporaryToken);
}
