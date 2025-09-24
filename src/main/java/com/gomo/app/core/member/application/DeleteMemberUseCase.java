package com.gomo.app.core.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.support.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteMemberUseCase {

	private final MemberService memberService;
	private final AuthTokenRepository authTokenRepository;

	@AuditLog(action = "DELETE_MEMBER")
	public void delete(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		authTokenRepository.deleteRefreshToken(memberId);
		member.delete();
	}
}
