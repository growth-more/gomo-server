package com.gomo.app.core.quest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.quest.application.usecase.OrderUpdateRepeatQuestUseCase;
import com.gomo.app.core.quest.presentation.request.OrderUpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats/orders")
@Presentation
public class OrderUpdateRepeatQuestApi {

	private final OrderUpdateRepeatQuestUseCase orderUpdateRepeatQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateRepeatQuestRequest request) {
		orderUpdateRepeatQuestUseCase.update(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.noContent().build();
	}
}
