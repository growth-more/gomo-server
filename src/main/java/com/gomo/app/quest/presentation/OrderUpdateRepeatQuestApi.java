package com.gomo.app.quest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.OrderUpdateRepeatQuestUseCase;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats/orders")
@Presentation
public class OrderUpdateRepeatQuestApi {

	private final OrderUpdateRepeatQuestUseCase orderUpdateRepeatQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth MemberId memberId, @RequestBody OrderUpdateRepeatQuestRequest request) {
		orderUpdateRepeatQuestUseCase.update(memberId.getId(), request);
		return ResponseEntity.noContent().build();
	}
}
