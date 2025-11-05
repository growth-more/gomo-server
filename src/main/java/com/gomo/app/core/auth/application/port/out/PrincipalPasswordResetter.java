package com.gomo.app.core.auth.application.port.out;

public interface PrincipalPasswordResetter {

	void reset(String email, String newPassword);
}
