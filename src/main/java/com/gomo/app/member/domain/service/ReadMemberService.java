package com.gomo.app.member.domain.service;

import java.util.UUID;

import com.gomo.app.common.DomainService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ReadMemberService {

	private final MemberRepository memberRepository;

	public Member find(UUID memberId) {
		return memberRepository.findById(MemberId.of(memberId))
			.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));
	}
}
