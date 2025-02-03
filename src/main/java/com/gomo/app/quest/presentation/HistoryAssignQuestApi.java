package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.HistoryReadAssignQuestUseCase;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.presentation.response.HistoryListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/histories")
@Presentation
public class HistoryAssignQuestApi {

	private final HistoryReadAssignQuestUseCase historyReadAssignQuestUseCase;

	@GetMapping
	public ResponseEntity<HistoryListAssignQuestResponse> findAll(@ModelAttribute PageRequest pageRequest) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		HistoryListAssignQuestResponse response = historyReadAssignQuestUseCase.findAll(ParticipantId.of(sessionMember.getId()), pageRequest);
		return ResponseEntity.ok(response);
	}
}
