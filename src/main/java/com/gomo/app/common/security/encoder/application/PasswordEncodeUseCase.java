package com.gomo.app.common.security.encoder.application;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.security.encoder.application.port.EncodePasswordPortIn;
import com.gomo.app.common.security.encoder.application.port.VerifyPasswordPortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class PasswordEncodeUseCase implements EncodePasswordPortIn, VerifyPasswordPortIn {

	private final PasswordEncoder passwordEncoder;

	@Override
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
