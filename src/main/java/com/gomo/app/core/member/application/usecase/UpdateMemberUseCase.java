package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateMemberUseCase {

	private final MemberService memberService;

	@AuditLog(action = "UPDATE_MEMBER")
	public void update(UUID memberId, String name, String motto) {
		Member member = memberService.find(MemberId.of(memberId));
		member.updateMemberInfo(name, motto);
	}
}
