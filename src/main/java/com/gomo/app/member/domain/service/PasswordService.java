package com.gomo.app.member.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.gomo.app.common.DomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PasswordService {

	private final PasswordEncoder passwordEncoder;

	public String encode(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

	public boolean matches(String originPassword, String inputPassword) {
		return passwordEncoder.matches(originPassword, inputPassword);
	}
}
