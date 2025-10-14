package com.gomo.app.support.auth.application.port;

public interface CreateAuthCodePortIn {

	/**
	 * Generates a new authentication code and sends it to the specified email.
	 *
	 * @param email The destination email address where the authentication code will be sent.
	 * @throws InvalidEmailFormatException if the provided email format is invalid.
	 * @throws RateLimitExceededException if requests are too frequent.
	 */
	String sendToEmail(String email);
}
