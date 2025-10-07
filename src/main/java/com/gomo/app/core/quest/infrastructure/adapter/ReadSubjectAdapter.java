package com.gomo.app.core.quest.infrastructure.adapter;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.interest.application.port.ReadInterestPortIn;
import com.gomo.app.core.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class ReadSubjectAdapter implements ReadSubjectPortOut {

	private final ReadInterestPortIn readInterestPortIn;

	@Override
	public List<SubjectDto> findAll(UUID participantId) {
		return readInterestPortIn.findAll(participantId).stream()
			.map(dto -> SubjectDto.of(
					dto.id(),
					dto.registrantId(),
					dto.name(),
					dto.proficiency().level()
				)
			).toList();
	}
}
