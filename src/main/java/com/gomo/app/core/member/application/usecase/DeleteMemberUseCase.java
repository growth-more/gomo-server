package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.auth.application.port.DeleteAuthTokenPortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class DeleteMemberUseCase {

	private final DeleteAuthTokenPortIn deleteAuthTokenPortIn;
	private final MemberService memberService;

	@AuditLog(action = "DELETE_MEMBER")
	public void delete(UUID memberId) {
		Member member = memberService.find(memberId);
		deleteAuthTokenPortIn.deleteRefreshToken(memberId);
		member.delete();
	}
}
