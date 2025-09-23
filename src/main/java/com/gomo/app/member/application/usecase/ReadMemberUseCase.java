package com.gomo.app.member.application.usecase;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.application.port.ReadMemberPortIn;
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
public class ReadMemberUseCase implements ReadMemberPortIn {

	private final MemberService memberService;
	private final PointWalletService pointWalletService;

	@Override
	public MemberDto find(UUID id) {
		Member member = memberService.find(MemberId.of(id));
		Balance balance = pointWalletService.findBalance(TransactorId.of(member.getId().getId()));
		return MemberDto.from(member, balance.getAmount());
	}
}
