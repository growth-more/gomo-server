package com.gomo.app.support.auth.adapter.out.client;

import java.util.Optional;
import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.in.MemberLoginProcessor;
import com.gomo.app.core.member.application.port.in.MemberOAuthLoginProcessor;
import com.gomo.app.support.auth.application.port.out.PrincipalLoginProcessor;
import com.gomo.app.support.auth.application.port.out.PrincipalOAuthLoginProcessor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class MemberClient implements PrincipalLoginProcessor, PrincipalOAuthLoginProcessor {

	private final MemberLoginProcessor memberLoginProcessor;
	private final MemberOAuthLoginProcessor memberOAuthLoginProcessor;

	@Override
	public UUID login(String email, String password) {
		return memberLoginProcessor.login(email, password);
	}

	@Override
	public Optional<UUID> login(String email) {
		return memberOAuthLoginProcessor.login(email);
	}
}
