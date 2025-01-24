package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.CreateAssignQuestUseCase;
import com.gomo.app.quest.application.DeleteAssignQuestUseCase;
import com.gomo.app.quest.application.ReadAssignQuestUseCase;
import com.gomo.app.quest.application.UpdateAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;
import com.gomo.app.quest.presentation.response.ListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns")
@Presentation
public class AssignQuestApi {

	private final CreateAssignQuestUseCase createAssignQuestUseCase;
	private final ReadAssignQuestUseCase readAssignQuestUseCase;
	private final UpdateAssignQuestUseCase updateAssignQuestUseCase;
	private final DeleteAssignQuestUseCase deleteAssignQuestUseCase;

	@PostMapping
	public ResponseEntity<CreateAssignQuestResponse> create(@RequestBody CreateAssignQuestRequest request) {
		return null;
	}

	@GetMapping
	public ResponseEntity<ListAssignQuestResponse> findAll(@RequestParam QuestType questType) {
		return null;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") AssignQuestId assignQuestId, @RequestBody UpdateAssignQuestRequest request) {
		return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") AssignQuestId assignQuestId) {
		return null;
	}
}
