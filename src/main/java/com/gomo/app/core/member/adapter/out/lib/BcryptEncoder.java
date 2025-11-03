package com.gomo.app.core.member.adapter.out.lib;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.PasswordEncodeManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class BcryptEncoder implements PasswordEncodeManager {

	private final PasswordEncoder passwordEncoder;

	@Override
	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	@Override
	public boolean verify(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}
