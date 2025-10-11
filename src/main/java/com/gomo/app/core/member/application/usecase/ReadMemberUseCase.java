package com.gomo.app.core.member.application.usecase;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.ReadMemberPortIn;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.point.application.port.ReadBalancePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ReadMemberUseCase implements ReadMemberPortIn {

	private final ReadBalancePortIn readBalancePortIn;
	private final MemberService memberService;

	@Override
	public MemberDto find(UUID id) {
		Member member = memberService.find(id);
		int balance = readBalancePortIn.find(member.getId());
		return MemberDto.from(member, balance);
	}
}
