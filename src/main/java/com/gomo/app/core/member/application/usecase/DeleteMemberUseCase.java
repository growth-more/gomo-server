package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.auth.application.port.DeleteAuthTokenPortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteMemberUseCase {

	private final MemberService memberService;
	private final DeleteAuthTokenPortIn deleteAuthTokenPortIn;

	@AuditLog(action = "DELETE_MEMBER")
	public void delete(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		deleteAuthTokenPortIn.deleteRefreshToken(memberId);
		member.delete();
	}
}
