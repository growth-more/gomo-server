package com.gomo.app.member.domain.service;

import java.util.UUID;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.OAuthUserInfo;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class MemberFactory {

	private final MemberRepository memberRepository;

	// public Member createMemberWithEmail() {
	//
	// }

	public Member createMemberWithOAuth(OAuthUserInfo userInfo, String providerName) {
		UUID uuid = UUIDGenerator.generate();
		MemberId memberId = MemberId.of(uuid);
		Member member = Member.of(
			memberId,
			Email.of(userInfo.getEmail()),
			null,
			null,
			MemberName.of(userInfo.getName()),
			null,
			LoginProvider.valueOf(providerName.toUpperCase())
		);
		return memberRepository.save(member);
	}
}
