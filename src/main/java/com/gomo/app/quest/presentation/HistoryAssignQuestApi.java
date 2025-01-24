package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.HistoryReadAssignQuestUseCase;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.presentation.response.HistoryListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/histories")
@Presentation
public class HistoryAssignQuestApi {

	private final HistoryReadAssignQuestUseCase historyReadAssignQuestUseCase;

	@GetMapping
	public ResponseEntity<HistoryListAssignQuestResponse> findAll(@RequestParam QuestType questType, @RequestParam PageRequest pageRequest) {
		return null;
	}
}
