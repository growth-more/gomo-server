package com.gomo.app.quest.presentation;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.quest.application.CalendarReadAssignQuestUseCase;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.presentation.response.CalendarListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/calendars")
@Presentation
public class CalendarAssignQuestApi {

	private final CalendarReadAssignQuestUseCase calendarReadAssignQuestUseCase;

	@GetMapping
	public ResponseEntity<CalendarListAssignQuestResponse> find(@Auth AuthInfo authInfo, @RequestParam boolean isCompleted,
		@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
		CalendarListAssignQuestResponse response = calendarReadAssignQuestUseCase.find(ParticipantId.of(authInfo.getMemberId()), isCompleted,
			startDateTime, endDateTime);
		return ResponseEntity.ok(response);
	}
}
