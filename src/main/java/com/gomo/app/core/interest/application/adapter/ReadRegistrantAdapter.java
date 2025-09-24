package com.gomo.app.core.interest.application.adapter;

import java.util.UUID;

import com.gomo.app.common.Adapter;
import com.gomo.app.core.interest.application.port.ReadRegistrantPortOut;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.member.application.port.ReadMemberPortIn;
import com.gomo.app.core.member.application.port.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadRegistrantAdapter implements ReadRegistrantPortOut {

	private final ReadMemberPortIn readMemberPortIn;

	@Override
	public RegistrantDto find(UUID registrantId) {
		MemberDto memberDto = readMemberPortIn.find(registrantId);
		return RegistrantDto.of(memberDto.id(), memberDto.subscriptionPlan());
	}
}
