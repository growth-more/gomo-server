package com.gomo.app.core.member.adapter.out;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.out.MemberCreateEventPublisher;
import com.gomo.app.core.point.application.port.CreatePointWalletPortIn;
import com.gomo.app.core.streak.application.port.CreateAchieverPortIn;

import lombok.RequiredArgsConstructor;

// TODO [2025-10-29] jhl221123 : spring modulith 도입해 이벤트 기반 통신으로 전환한 후 제거해야합니다.
@RequiredArgsConstructor
@Adapter
class MemberEventPublisher implements MemberCreateEventPublisher {

	private final CreatePointWalletPortIn createPointWalletPortIn;
	private final CreateAchieverPortIn createAchieverPortIn;

	@Override
	public void publish(UUID memberId) {
		createPointWalletPortIn.create(memberId);
		createAchieverPortIn.create(memberId);
	}
}
