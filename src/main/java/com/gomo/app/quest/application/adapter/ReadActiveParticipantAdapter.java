package com.gomo.app.quest.application.adapter;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.common.Adapter;
import com.gomo.app.member.application.port.ReadActiveMemberPortIn;
import com.gomo.app.quest.application.port.ReadActiveParticipantPortOut;
import com.gomo.app.quest.application.port.dto.ActiveParticipantDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadActiveParticipantAdapter implements ReadActiveParticipantPortOut {

	private final ReadActiveMemberPortIn readActiveMemberPortIn;

	@Override
	public List<ActiveParticipantDto> findAll(LocalDate lastLoginDate) {
		return readActiveMemberPortIn.findAll(lastLoginDate).stream()
			.map(activeMember -> ActiveParticipantDto.of(activeMember.id()))
			.toList();
	}
}
