package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.List;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.response.CalendarListAssignQuestResponse;
import com.gomo.app.quest.presentation.response.CalendarReadAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CalendarReadAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public CalendarListAssignQuestResponse find(ParticipantId participantId, boolean isCompleted, LocalDateTime startDate, LocalDateTime endDate) {
		List<CalendarReadAssignQuestResponse> calendars;
		if (isCompleted) {
			calendars = assignQuestRepository.findByQuestParticipantIdAndCompletedDateTimeBetween(participantId, startDate, endDate)
				.stream()
				.map(CalendarReadAssignQuestResponse::of)
				.toList();
		} else {
			calendars = assignQuestRepository.findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(participantId, startDate, endDate)
				.stream()
				.map(CalendarReadAssignQuestResponse::of)
				.toList();
		}
		System.out.println("calendars: " + calendars.size());
		return CalendarListAssignQuestResponse.of(calendars);
	}
}
