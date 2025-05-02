package com.gomo.app.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateMemberUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;

	public void update(UUID memberId, UpdateMemberRequest request) {
		Member member = memberService.find(MemberId.of(memberId));
		member.updateMemberInfo(request.getName(), request.getMotto());
	}
}
