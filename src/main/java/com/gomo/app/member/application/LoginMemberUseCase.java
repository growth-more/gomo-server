package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LoginMemberUseCase {

	private final MemberRepository memberRepository;

	public LoginMemberResponse login(LoginMemberRequest request) {
		return null;
	}
}
