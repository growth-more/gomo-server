package com.gomo.app.core.member.adapter.out.client;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.MemberLogoutProcessor;
import com.gomo.app.support.auth.application.port.in.RefreshTokenDeleter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class AuthClient implements MemberLogoutProcessor {

	// TODO [2025-11-02] jhl221123 : 회원 탈퇴 도메인 이벤트를 발행하고, 해당 클라이언트는 제거해야 모듈간 의존성 방향을 올바르게 유지할 수 있습니다.
	private final RefreshTokenDeleter refreshTokenDeleter;

	@Override
	public void logout(UUID memberId) {
		refreshTokenDeleter.delete(memberId);
	}
}
