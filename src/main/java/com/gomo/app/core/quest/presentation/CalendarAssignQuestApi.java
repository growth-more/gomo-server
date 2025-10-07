package com.gomo.app.core.quest.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.application.port.command.CalendarAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.CalendarAssignQuestDto;
import com.gomo.app.core.quest.application.usecase.CalendarAssignQuestUseCase;
import com.gomo.app.core.quest.presentation.response.CalendarAssignQuestResponse;
import com.gomo.app.core.quest.presentation.response.ListCalendarAssignQuestResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/calendars")
@CoreApi
public class CalendarAssignQuestApi {

	private final CalendarAssignQuestUseCase calendarAssignQuestUseCase;

	@GetMapping
	public ResponseEntity<ListCalendarAssignQuestResponse> find(@Auth AuthInfo authInfo, @RequestParam boolean isCompleted,
		@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
		CalendarAssignQuestCommand command = CalendarAssignQuestCommand.of(authInfo.getMemberId(), isCompleted, startDateTime, endDateTime);
		List<CalendarAssignQuestDto> calendarDtos = calendarAssignQuestUseCase.find(command);
		return ResponseEntity.ok(ListCalendarAssignQuestResponse.of(calendarDtos.stream().map(CalendarAssignQuestResponse::from).toList()));
	}
}
