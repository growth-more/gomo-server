package com.gomo.app.core.interest.adapter.out.client;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.interest.application.port.out.ReadRegistrantPort;
import com.gomo.app.core.member.application.port.ReadMemberPortIn;
import com.gomo.app.core.member.application.port.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadMemberClient implements ReadRegistrantPort {

	private final ReadMemberPortIn readMemberPortIn;

	@Override
	public RegistrantDto find(UUID registrantId) {
		MemberDto memberDto = readMemberPortIn.find(registrantId);
		return RegistrantDto.of(memberDto.id(), memberDto.subscriptionPlan());
	}
}
