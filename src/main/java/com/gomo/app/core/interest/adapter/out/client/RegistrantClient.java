package com.gomo.app.core.interest.adapter.out.client;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.dto.RegistrantDto;
import com.gomo.app.core.interest.application.port.out.RegistrantReader;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class RegistrantClient implements RegistrantReader {

	private final MemberReader memberReader;

	@Override
	public RegistrantDto find(UUID registrantId) {
		MemberDto memberDto = memberReader.read(registrantId);
		return RegistrantDto.of(memberDto.id(), memberDto.subscriptionPlan());
	}
}
