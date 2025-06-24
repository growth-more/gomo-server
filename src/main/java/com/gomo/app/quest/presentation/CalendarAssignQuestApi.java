package com.gomo.app.quest.presentation;

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
	public ResponseEntity<CalendarListAssignQuestResponse> findAll(@Auth AuthInfo authInfo, @RequestParam int year, @RequestParam int month, @RequestParam int day) {
		CalendarListAssignQuestResponse response = calendarReadAssignQuestUseCase.findAll(ParticipantId.of(authInfo.getMemberId()), year, month, day);
		return ResponseEntity.ok(response);
	}
}
