package com.gomo.app.core.member.adapter.out.client;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.MemberLogoutProcessor;
import com.gomo.app.support.auth.application.port.RefreshTokenDeleter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class AuthClient implements MemberLogoutProcessor {

	private final RefreshTokenDeleter refreshTokenDeleter;

	@Override
	public void logout(UUID memberId) {
		refreshTokenDeleter.delete(memberId);
	}
}
