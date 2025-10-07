package com.gomo.app.core.quest.infrastructure.adapter;

import java.time.LocalDate;
import java.util.List;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.ReadActiveMemberPortIn;
import com.gomo.app.core.quest.application.port.ReadActiveParticipantPortOut;
import com.gomo.app.core.quest.application.port.dto.ActiveParticipantDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadActiveParticipantAdapter implements ReadActiveParticipantPortOut {

	private final ReadActiveMemberPortIn readActiveMemberPortIn;

	@Override
	public List<ActiveParticipantDto> findAll(LocalDate lastLoginDate) {
		return readActiveMemberPortIn.findAll(lastLoginDate).stream()
			.map(activeMember -> ActiveParticipantDto.of(
				activeMember.id(),
				activeMember.questProperty().dailyThreshold(),
				activeMember.questProperty().weeklyThreshold(),
				activeMember.questProperty().monthlyThreshold()
			)).toList();
	}
}
