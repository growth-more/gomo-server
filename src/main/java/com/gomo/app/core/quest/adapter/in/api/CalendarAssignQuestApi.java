package com.gomo.app.core.quest.adapter.in.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.quest.adapter.in.api.response.ListAssignQuestResponse;
import com.gomo.app.core.quest.adapter.in.api.response.ReadAssignQuestResponse;
import com.gomo.app.core.quest.application.port.command.ListAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.application.port.in.AssignQuestReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/calendars")
@CoreApi
public class CalendarAssignQuestApi {

	private final AssignQuestReader assignQuestReader;

	@GetMapping
	public ResponseEntity<ListAssignQuestResponse> find(@Session SessionInfo sessionInfo, @RequestParam boolean isCompleted,
		@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
		ListAssignQuestCommand command = ListAssignQuestCommand.of(sessionInfo.getPrincipalId(), isCompleted, startDateTime, endDateTime);
		List<AssignQuestDto> calendarDtos = assignQuestReader.readAll(command);
		return ResponseEntity.ok(ListAssignQuestResponse.of(calendarDtos.stream().map(ReadAssignQuestResponse::from).toList()));
	}
}
