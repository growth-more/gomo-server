package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.CompleteAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.presentation.request.CompleteAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/complete")
@Presentation
public class CompleteAssignQuestApi {

	private final CompleteAssignQuestUseCase completeAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> complete(@PathVariable("id") AssignQuestId assignQuestId, @RequestBody CompleteAssignQuestRequest request) {
		return null;
	}
}
