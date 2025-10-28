package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class DeleteProfileImageUseCase {

	private final MemberService memberService;

	@AuditLog(action = "DELETE_PROFILE_IMAGE")
	public void delete(UUID memberId) {
		Member member = memberService.find(memberId);
		member.deleteProfile();
	}
}
