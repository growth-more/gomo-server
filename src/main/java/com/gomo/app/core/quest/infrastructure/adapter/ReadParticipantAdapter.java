package com.gomo.app.core.quest.infrastructure.adapter;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberReader;
import com.gomo.app.core.quest.application.port.ReadParticipantPortOut;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadParticipantAdapter implements ReadParticipantPortOut {

	private final MemberReader memberReader;

	@Override
	public ParticipantDto find(UUID participantId) {
		MemberDto memberDto = memberReader.read(participantId);
		return ParticipantDto.of(
			memberDto.id(),
			memberDto.questProperty().dailyThreshold(),
			memberDto.questProperty().weeklyThreshold(),
			memberDto.questProperty().monthlyThreshold()
		);
	}
}
