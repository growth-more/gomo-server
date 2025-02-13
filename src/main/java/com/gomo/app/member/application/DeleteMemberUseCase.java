package com.gomo.app.member.application;

import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.exception.MemberErrorCode;
import com.gomo.app.member.exception.MemberNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteMemberUseCase {

	private final MemberRepository memberRepository;

	public void delete(MemberId memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND, "member has not found"));
		member.delete();
	}
}
