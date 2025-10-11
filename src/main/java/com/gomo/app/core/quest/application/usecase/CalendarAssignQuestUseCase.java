package com.gomo.app.core.quest.application.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.quest.application.port.command.CalendarAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.CalendarAssignQuestDto;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CalendarAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public List<CalendarAssignQuestDto> find(CalendarAssignQuestCommand command) {
		UUID targetId = command.participantId();
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
