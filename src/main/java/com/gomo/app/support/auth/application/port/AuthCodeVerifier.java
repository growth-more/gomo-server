package com.gomo.app.support.auth.application.port;

import com.gomo.app.support.auth.exception.InvalidAuthCodeException;

public interface AuthCodeVerifier {

	/**
	 * Verifies if the provided authentication code is valid for the given email.
	 * <p>
	 * This method has no return value. A successful execution implies that the code is valid.
	 * If the code is invalid or expired, an exception will be thrown.
	 *
	 * @param email The email address associated with the authentication code.
	 * @param authCode The user-submitted code to be verified.
	 * @throws InvalidAuthCodeException if the code does not match or if the code has expired.
	 */
	void verify(String email, String authCode);
}
