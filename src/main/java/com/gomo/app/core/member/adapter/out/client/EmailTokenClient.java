package com.gomo.app.core.member.adapter.out.client;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.EmailTokenCreator;
import com.gomo.app.core.member.application.port.out.EmailTokenVerifier;
import com.gomo.app.support.auth.application.port.JwtCreator;
import com.gomo.app.support.auth.application.port.JwtVerifier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class EmailTokenClient implements EmailTokenCreator, EmailTokenVerifier {

	private final JwtCreator jwtCreator;
	private final JwtVerifier jwtVerifier;

	@Override
	public String create(String email, long expiration) {
		return jwtCreator.createTemporaryToken(email, expiration);
	}

	@Override
	public void verify(String temporaryToken) {
		// TODO [2025-10-18] jhl221123 : jwt 형식 뿐 아니라 내부 이메일이 요청한 이메일과 같은지 검증이 필요합니다.
		// TODO [2025-10-19] jhl221123 : 커스텀 예외를 반환하도록 수정해야합니다.
		if (!jwtVerifier.verify(temporaryToken)) {
			throw new IllegalArgumentException("Invalid temporary token");
		}
	}
}
