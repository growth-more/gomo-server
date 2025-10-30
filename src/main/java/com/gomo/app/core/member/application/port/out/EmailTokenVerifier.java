package com.gomo.app.core.member.application.port.out;

public interface EmailTokenVerifier {

	/**
	 * Verifies the validity of a temporary token, typically used for processes like email verification.
	 *
	 * @param temporaryToken The temporary token string to be validated.
	 * @throws IllegalArgumentException if the token is invalid, expired, or malformed.
	 */
	void verify(String temporaryToken);
}
