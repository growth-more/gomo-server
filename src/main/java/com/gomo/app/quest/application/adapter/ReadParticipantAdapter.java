package com.gomo.app.quest.application.adapter;

import java.util.UUID;

import com.gomo.app.common.Adapter;
import com.gomo.app.member.application.port.ReadMemberPortIn;
import com.gomo.app.member.application.port.dto.MemberDto;
import com.gomo.app.quest.application.port.ReadParticipantPortOut;
import com.gomo.app.quest.application.port.dto.ParticipantDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadParticipantAdapter implements ReadParticipantPortOut {

	private final ReadMemberPortIn readMemberPortIn;

	@Override
	public ParticipantDto find(UUID participantId) {
		MemberDto memberDto = readMemberPortIn.find(participantId);
		return ParticipantDto.of(
			memberDto.id(),
			memberDto.questProperty().dailyThreshold(),
			memberDto.questProperty().weeklyThreshold(),
			memberDto.questProperty().monthlyThreshold()
		);
	}
}
