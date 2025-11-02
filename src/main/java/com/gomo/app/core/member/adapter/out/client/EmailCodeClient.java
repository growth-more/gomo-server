package com.gomo.app.core.member.adapter.out.client;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.EmailCodeSender;
import com.gomo.app.core.member.application.port.out.EmailCodeVerifier;
import com.gomo.app.support.auth.application.port.in.AuthCodeIssuer;
import com.gomo.app.support.auth.application.port.in.AuthCodeVerifier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class EmailCodeClient implements EmailCodeSender, EmailCodeVerifier {

	private final AuthCodeIssuer authCodeIssuer;
	private final AuthCodeVerifier authCodeVerifier;

	// TODO [2025-11-02] jhl221123 : 인증 모듈의 책임입니다.
	@Override
	public void send(String email) {
		authCodeIssuer.sendToEmail(email);
	}

	@Override
	public void verify(String email, String emailCode) {
		authCodeVerifier.verify(email, emailCode);
	}
}
