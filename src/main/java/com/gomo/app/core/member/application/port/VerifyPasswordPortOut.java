package com.gomo.app.core.member.application.port;

public interface VerifyPasswordPortOut {

	boolean matches(String rawPassword, String encodedPassword);
}
