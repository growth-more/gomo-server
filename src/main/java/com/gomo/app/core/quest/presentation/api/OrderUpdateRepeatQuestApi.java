package com.gomo.app.core.quest.presentation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.application.usecase.OrderUpdateRepeatQuestUseCase;
import com.gomo.app.core.quest.presentation.api.request.OrderUpdateRepeatQuestRequest;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats/orders")
@CoreApi
public class OrderUpdateRepeatQuestApi {

	private final OrderUpdateRepeatQuestUseCase orderUpdateRepeatQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateRepeatQuestRequest request) {
		orderUpdateRepeatQuestUseCase.update(request.toCommand(authInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
