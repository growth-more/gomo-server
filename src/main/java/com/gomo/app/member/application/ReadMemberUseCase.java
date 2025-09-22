package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.application.port.dto.MemberDto;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointWalletService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMemberUseCase {

	private final MemberService memberService;
	private final PointWalletService pointWalletService;

	public MemberDto find(UUID memberId) {
		Member member = memberService.find(MemberId.of(memberId));
		Balance balance = pointWalletService.findBalance(TransactorId.of(member.getId().getId()));
		return MemberDto.from(member, balance.getAmount());
	}
}
