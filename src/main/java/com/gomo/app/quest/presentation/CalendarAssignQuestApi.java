package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
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
	public ResponseEntity<CalendarListAssignQuestResponse> findAll(@RequestParam int year, @RequestParam int month) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		CalendarListAssignQuestResponse response = calendarReadAssignQuestUseCase.findAll(ParticipantId.of(sessionMember.getId()), year, month);
		return ResponseEntity.ok(response);
	}
}
