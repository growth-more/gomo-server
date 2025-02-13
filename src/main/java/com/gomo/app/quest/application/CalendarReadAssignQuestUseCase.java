package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.List;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.response.CalendarListAssignQuestResponse;
import com.gomo.app.quest.presentation.response.CalendarReadAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CalendarReadAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public CalendarListAssignQuestResponse findAll(ParticipantId participantId, int year, int month) {
		LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
		LocalDateTime end = start.plusMonths(1).minusSeconds(1);
		List<CalendarReadAssignQuestResponse> historyQuests = assignQuestRepository.findByQuestParticipantIdAndStartDateTimeBetween(participantId, start, end)
			.stream()
			.map(CalendarReadAssignQuestResponse::of)
			.toList();

		return CalendarListAssignQuestResponse.of(historyQuests);
	}

}
