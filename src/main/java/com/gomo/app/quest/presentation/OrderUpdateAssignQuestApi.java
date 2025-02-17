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
import com.gomo.app.quest.application.OrderUpdateAssignQuestUseCase;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/orders")
@Presentation
public class OrderUpdateAssignQuestApi {

	private final OrderUpdateAssignQuestUseCase orderUpdateAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth MemberId memberId, @RequestBody OrderUpdateAssignQuestRequest request) {
		orderUpdateAssignQuestUseCase.update(memberId.getId(), request);
		return ResponseEntity.noContent().build();
	}
}
