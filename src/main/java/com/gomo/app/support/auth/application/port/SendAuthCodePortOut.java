package com.gomo.app.support.auth.application.port;

public interface SendAuthCodePortOut {

	void toEmail(String email, String authCode);
}
