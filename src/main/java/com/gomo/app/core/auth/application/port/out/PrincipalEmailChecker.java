package com.gomo.app.core.auth.application.port.out;

public interface PrincipalEmailChecker {

	boolean exists(String email);
}
