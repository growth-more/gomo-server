package com.gomo.app.quest.application;

import java.time.LocalDateTime;
import java.util.List;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.InvalidPeriodTypeException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.quest.presentation.response.CalendarListAssignQuestResponse;
import com.gomo.app.quest.presentation.response.CalendarReadAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CalendarReadAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public CalendarListAssignQuestResponse findAll(ParticipantId participantId, int year, int month, int day, String periodType) {
		LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0);
		LocalDateTime end = getEndDateTime(start, periodType);
		List<CalendarReadAssignQuestResponse> calendars = assignQuestRepository.findByQuestParticipantIdAndStartDateTimeBetween(participantId, start, end)
			.stream()
			.map(CalendarReadAssignQuestResponse::of)
			.toList();

		return CalendarListAssignQuestResponse.of(calendars);
	}

	private LocalDateTime getEndDateTime(LocalDateTime start, String periodType) {
		if ("DAY".equals(periodType)) {
			return start.plusDays(1).minusSeconds(1);
		} else if ("MONTH".equals(periodType)) {
			return start.plusMonths(1).minusSeconds(1);
		} else {
			throw new InvalidPeriodTypeException(AssignQuestErrorCode.INVALID_PERIOD_TYPE);
		}
	}
}
