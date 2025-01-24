package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.ConfirmAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/confirm")
@Presentation
public class ConfirmAssignQuestApi {

	private final ConfirmAssignQuestUseCase confirmAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> confirm(@PathVariable("id") AssignQuestId assignQuestId) {
		return null;
	}
}
