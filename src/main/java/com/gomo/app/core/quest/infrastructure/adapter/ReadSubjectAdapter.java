package com.gomo.app.core.quest.infrastructure.adapter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.in.ReadInterestUseCase;
import com.gomo.app.core.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadSubjectAdapter implements ReadSubjectPortOut {

	private final ReadInterestUseCase readInterestUseCase;

	@Override
	public List<SubjectDto> findAll(UUID participantId) {
		return readInterestUseCase.findAll(participantId).stream()
			.map(dto -> SubjectDto.of(
				dto.id(),
				dto.registrantId(),
				dto.name(),
				dto.proficiency().level()
			)).toList();
	}

	@Override
	public List<SubjectDto> findAllByParticipantIds(Set<UUID> participantIds) {
		return readInterestUseCase.findAllByRegistrantIds(participantIds).stream()
			.map(dto -> SubjectDto.of(
				dto.id(),
				dto.registrantId(),
				dto.name(),
				dto.proficiency().level()
			)).toList();
	}
}
