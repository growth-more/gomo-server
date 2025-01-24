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
import com.gomo.app.quest.application.CreateRepeatQuestUseCase;
import com.gomo.app.quest.application.DeleteRepeatQuestUseCase;
import com.gomo.app.quest.application.ReadRepeatQuestUseCase;
import com.gomo.app.quest.application.UpdateRepeatQuestUseCase;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;
import com.gomo.app.quest.presentation.response.ListRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats")
@Presentation
public class RepeatQuestApi {

	private final CreateRepeatQuestUseCase createRepeatQuestUseCase;
	private final ReadRepeatQuestUseCase readRepeatQuestUseCase;
	private final UpdateRepeatQuestUseCase updateRepeatQuestUseCase;
	private final DeleteRepeatQuestUseCase deleteRepeatQuestUseCase;

	@PostMapping
	public ResponseEntity<CreateRepeatQuestResponse> create(@RequestBody CreateRepeatQuestRequest request) {
		return null;
	}

	@GetMapping
	public ResponseEntity<ListRepeatQuestResponse> findAll(@RequestParam QuestType questType) {
		return null;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") RepeatQuestId repeatQuestId, @RequestBody UpdateRepeatQuestRequest request) {
		return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") RepeatQuestId repeatQuestId) {
		return null;
	}
}
