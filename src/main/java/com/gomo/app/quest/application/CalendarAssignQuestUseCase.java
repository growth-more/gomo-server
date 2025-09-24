package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.List;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.application.port.command.CalendarAssignQuestCommand;
import com.gomo.app.quest.application.port.dto.CalendarAssignQuestDto;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CalendarAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public List<CalendarAssignQuestDto> find(CalendarAssignQuestCommand command) {
		ParticipantId targetId = ParticipantId.of(command.participantId());
		LocalDateTime startDate = command.startDate();
		LocalDateTime endDate = command.endDate();
		List<CalendarAssignQuestDto> calendars;
		if (command.isCompleted()) {
			calendars = assignQuestRepository.findByQuestParticipantIdAndCompletedDateTimeBetween(targetId, startDate, endDate)
				.stream()
				.map(CalendarAssignQuestDto::of)
				.toList();
		} else {
			calendars = assignQuestRepository.findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(targetId, startDate, endDate)
				.stream()
				.map(CalendarAssignQuestDto::of)
				.toList();
		}
		return calendars;
	}
}
