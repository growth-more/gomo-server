package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.logging.AuditLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class UpdateHandleUseCase {

	private final MemberService memberService;

	@AuditLog(action = "UPDATE_HANDLE")
	public void update(UUID memberId, String handle) {
		Member member = memberService.find(memberId);
		memberService.checkHandleDuplicated(Handle.of(handle));
		member.updateHandle(handle);
	}
}
