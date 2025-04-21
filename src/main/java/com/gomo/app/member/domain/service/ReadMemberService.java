package com.gomo.app.member.domain.service;

import java.util.UUID;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.exception.MemberErrorCode;
import com.gomo.app.member.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ReadMemberService {

	private final MemberRepository memberRepository;

	public Member find(UUID memberId) {
		return memberRepository.findById(MemberId.of(memberId))
			.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND, "Member not found with id: " + memberId));
	}
}
