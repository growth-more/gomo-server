package com.gomo.app.core.member.adapter.out.client;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.EmailTokenVerifier;
import com.gomo.app.core.auth.application.port.out.JwtVerifier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class EmailTokenClient implements EmailTokenVerifier {

	private final JwtVerifier jwtVerifier;

	// TODO [2025-11-02] jhl221123 : 인증 모듈의 책임입니다.
	@Override
	public void verify(String temporaryToken) {
		// TODO [2025-10-18] jhl221123 : jwt 형식 뿐 아니라 내부 이메일이 요청한 이메일과 같은지 검증이 필요합니다.
		// TODO [2025-10-19] jhl221123 : 커스텀 예외를 반환하도록 수정해야합니다.
		if (!jwtVerifier.verify(temporaryToken)) {
			throw new IllegalArgumentException("Invalid temporary token");
		}
	}
}
