package com.gomo.app.core.member.application.port.out;

public interface EmailCodeVerifier {

	/**
	 * Verifies an authentication code sent to a specific email address.
	 *
	 * @param email     The email address for which the code is being verified.
	 * @param emailCode The authentication code provided by the user.
	 */
	void verify(String email, String emailCode);
}
