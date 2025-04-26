package com.gomo.app.quest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.Presentation;
import com.gomo.app.quest.application.OrderUpdateRepeatQuestUseCase;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats/orders")
@Presentation
public class OrderUpdateRepeatQuestApi {

	private final OrderUpdateRepeatQuestUseCase orderUpdateRepeatQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateRepeatQuestRequest request) {
		orderUpdateRepeatQuestUseCase.update(authInfo.getMemberId(), request);
		return ResponseEntity.noContent().build();
	}
}
