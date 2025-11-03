package com.gomo.app.core.quest.adapter.out.client;

import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.member.application.port.dto.MemberDto;
import com.gomo.app.core.member.application.port.in.MemberReader;
import com.gomo.app.core.quest.application.port.dto.ParticipantDto;
import com.gomo.app.core.quest.application.port.out.ParticipantReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ParticipantClient implements ParticipantReader {

	private final MemberReader memberReader;

	@Override
	public ParticipantDto read(UUID participantId) {
		MemberDto memberDto = memberReader.read(participantId);
		return ParticipantDto.of(
			memberDto.id(),
			memberDto.questProperty().dailyThreshold(),
			memberDto.questProperty().weeklyThreshold(),
			memberDto.questProperty().monthlyThreshold()
		);
	}
}
