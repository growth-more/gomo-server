package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateHandleUseCase {

	private final MemberService memberService;

	@AuditLog(action = "UPDATE_HANDLE")
	public void update(UUID memberId, String handle) {
		Member member = memberService.find(MemberId.of(memberId));
		memberService.checkHandleDuplicated(Handle.of(handle));
		member.updateHandle(handle);
	}
}
