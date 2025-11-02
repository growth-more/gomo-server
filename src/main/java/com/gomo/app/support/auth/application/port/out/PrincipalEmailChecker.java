package com.gomo.app.support.auth.application.port.out;

public interface PrincipalEmailChecker {

	boolean exists(String email);
}
