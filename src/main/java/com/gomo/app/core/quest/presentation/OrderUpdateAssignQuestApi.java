package com.gomo.app.core.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.core.quest.application.OrderUpdateAssignQuestUseCase;
import com.gomo.app.core.quest.presentation.request.OrderUpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/orders")
@Presentation
public class OrderUpdateAssignQuestApi {

	private final OrderUpdateAssignQuestUseCase orderUpdateAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateAssignQuestRequest request) {
		orderUpdateAssignQuestUseCase.update(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.noContent().build();
	}
}
