package com.gomo.app.core.member.application.port.out;

public interface EmailCodeSender {

	/**
	 * Sends an authentication code to the specified email address.
	 *
	 * @param email The target email address to which the code will be sent.
	 */
	void send(String email);
}
