package com.gomo.app.quest.application.adapter;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.Adapter;
import com.gomo.app.interest.application.port.ReadInterestPortIn;
import com.gomo.app.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.quest.application.port.dto.SubjectDto;

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
