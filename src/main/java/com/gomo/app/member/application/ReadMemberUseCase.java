package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointWalletService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMemberUseCase {

	private final MemberService memberService;
	private final PointWalletService pointWalletService;

	public ReadMemberResponse find(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		Balance balance = pointWalletService.findBalance(TransactorId.of(member.getId().getId()));
		return ReadMemberResponse.of(member, balance.getAmount());
	}

	// TODO <jhl221123> to <nurdy>: check 전용 유스케이스로 분리하면 좋을 것 같습니다.
	public void checkHandleDuplicated(String handle){
		memberService.checkHandleDuplicated(Handle.of(handle));
	}
}
