package com.gomo.app.core.member.application.port.out;

public interface EmailCodeSender {

	// TODO [2025-11-02] jhl221123 : 인증 모듈의 책임입니다.

	/**
	 * Sends an authentication code to the specified email address.
	 *
	 * @param email The target email address to which the code will be sent.
	 */
	void send(String email);
}
