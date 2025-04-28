package com.gomo.app.member.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

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

	public void delete(UUID memberId) {
		// TODO <jhl221123> to <nurdy>: 회원을 삭제할 때, 추가적인 검증이나 부가 작업에 대해 고민해보면 좋을 것 같습니다.
		Member member = memberService.find(MemberId.of(memberId));
		member.delete();
	}

	public void deleteProfile(UUID memberId){
		Member member = memberService.find(MemberId.of(memberId));
		member.deleteProfile();
	}

	public void deleteBanner(UUID memberId){
		Member member = memberService.find(MemberId.of(memberId));
		member.deleteBanner();
	}
}
