package com.gomo.app.support.auth.application.port;

public interface SendAuthCodePortOut {

	/**
	 * Sends a given authentication code to the specified email address.
	 *
	 * @param email The recipient's email address.
	 * @param authCode The authentication code to be sent.
	 * @throws IllegalStateException if the email delivery fails.
	 */
	void toEmail(String email, String authCode);
}
