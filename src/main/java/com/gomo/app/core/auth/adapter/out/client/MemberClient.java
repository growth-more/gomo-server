package com.gomo.app.core.auth.adapter.out.client;

import java.util.Optional;
import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.in.EmailChecker;
import com.gomo.app.core.member.application.port.in.MemberCreator;
import com.gomo.app.core.member.application.port.in.MemberLoginProcessor;
import com.gomo.app.core.member.application.port.in.MemberOAuthLoginProcessor;
import com.gomo.app.core.auth.application.port.command.CreatePrincipalCommand;
import com.gomo.app.core.auth.application.port.out.PrincipalCreator;
import com.gomo.app.core.auth.application.port.out.PrincipalEmailChecker;
import com.gomo.app.core.auth.application.port.out.PrincipalLoginProcessor;
import com.gomo.app.core.auth.application.port.out.PrincipalOAuthLoginProcessor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class MemberClient implements PrincipalCreator, PrincipalEmailChecker, PrincipalLoginProcessor, PrincipalOAuthLoginProcessor {

	private final MemberCreator memberCreator;
	private final EmailChecker emailChecker;
	private final MemberLoginProcessor memberLoginProcessor;
	private final MemberOAuthLoginProcessor memberOAuthLoginProcessor;

	@Override
	public UUID create(CreatePrincipalCommand command) {
		return memberCreator.create(command.toMemberCommand());
	}

	@Override
	public boolean exists(String email) {
		return emailChecker.exists(email);
	}

	@Override
	public UUID login(String email, String password) {
		return memberLoginProcessor.login(email, password);
	}

	@Override
	public Optional<UUID> login(String email) {
		return memberOAuthLoginProcessor.login(email);
	}
}
