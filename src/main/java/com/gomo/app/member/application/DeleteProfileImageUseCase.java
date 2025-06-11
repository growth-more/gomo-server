package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteProfileImageUseCase {

	private final MemberService memberService;

	public void delete(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		member.deleteProfile();
	}
}
