package com.gomo.app.core.auth.application.port.in;

public interface AuthTokenIssuer {

	/**
	 * Verifies an authentication code sent to a specific email address.
	 * Upon successful verification, it creates a temporary token for subsequent processes like completing registration.
	 *
	 * @param email    The email address for which the code is being verified.
	 * @param emailCode The authentication code provided by the user.
	 * @return A temporary token (JWT) that authorizes the next step in the workflow.
	 */
	String issue(String email, String emailCode);
}
