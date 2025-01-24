package com.gomo.app.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.ReadQuestPropertyUseCase;
import com.gomo.app.member.application.UpdateQuestPropertyUseCase;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;
import com.gomo.app.member.presentation.response.ReadQuestPropertyResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/properties/quests")
@Presentation
public class QuestPropertyApi {

	private final ReadQuestPropertyUseCase readQuestPropertyUseCase;
	private final UpdateQuestPropertyUseCase updateQuestPropertyUseCase;

	@GetMapping
	public ResponseEntity<ReadQuestPropertyResponse> find() {
		return null;
	}

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody UpdateQuestPropertyRequest request) {
		return null;
	}
}
