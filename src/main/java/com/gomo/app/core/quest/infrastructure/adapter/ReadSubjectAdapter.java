package com.gomo.app.core.quest.infrastructure.adapter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.in.InterestReader;
import com.gomo.app.core.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadSubjectAdapter implements ReadSubjectPortOut {

	private final InterestReader interestReader;

	@Override
	public List<SubjectDto> findAll(UUID participantId) {
		return interestReader.readAll(participantId).stream()
			.map(dto -> SubjectDto.of(
				dto.id(),
				dto.registrantId(),
				dto.name(),
				dto.proficiency().level()
			)).toList();
	}

	@Override
	public List<SubjectDto> findAllByParticipantIds(Set<UUID> participantIds) {
		return interestReader.readAllByRegistrantIds(participantIds).stream()
			.map(dto -> SubjectDto.of(
				dto.id(),
				dto.registrantId(),
				dto.name(),
				dto.proficiency().level()
			)).toList();
	}
}
