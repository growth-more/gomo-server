package com.gomo.app.support.auth.application.port;

public interface VerifyAuthCodePortIn {

	void verify(String email, String authCode);
}
