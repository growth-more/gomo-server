package com.gomo.app.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.OrderUpdateAssignQuestUseCase;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/orders")
@Presentation
public class OrderUpdateAssignQuestApi {

	private final OrderUpdateAssignQuestUseCase orderUpdateAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody OrderUpdateAssignQuestRequest request) {
		return null;
	}
}
