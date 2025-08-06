package com.gomo.app.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.auth.domain.repository.AuthTokenRepository;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class DeleteMemberUseCase {

	private final MemberService memberService;
	private final AuthTokenRepository authTokenRepository;

	public void delete(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		authTokenRepository.deleteRefreshToken(memberId);
		member.delete();
	}
}
