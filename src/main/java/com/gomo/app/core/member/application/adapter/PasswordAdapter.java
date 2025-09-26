package com.gomo.app.core.member.application.adapter;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.EncodePasswordPortOut;
import com.gomo.app.core.member.application.port.VerifyPasswordPortOut;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
public class PasswordAdapter implements EncodePasswordPortOut, VerifyPasswordPortOut {

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
