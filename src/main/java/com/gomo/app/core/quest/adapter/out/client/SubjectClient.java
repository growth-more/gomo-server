package com.gomo.app.core.quest.adapter.out.client;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.in.InterestReader;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;
import com.gomo.app.core.quest.application.port.out.SubjectReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class SubjectClient implements SubjectReader {

	private final InterestReader interestReader;

	@Override
	public List<SubjectDto> readAll(UUID participantId) {
		return interestReader.readAll(participantId).stream()
			.map(dto -> SubjectDto.of(
				dto.id(),
				dto.registrantId(),
				dto.name(),
				dto.proficiency().level()
			)).toList();
	}

	@Override
	public List<SubjectDto> readAllByParticipantIds(Set<UUID> participantIds) {
		return interestReader.readAllByRegistrantIds(participantIds).stream()
			.map(dto -> SubjectDto.of(
				dto.id(),
				dto.registrantId(),
				dto.name(),
				dto.proficiency().level()
			)).toList();
	}
}
