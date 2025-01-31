package com.gomo.app.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.OrderUpdateMajorInterestUseCase;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/majors/orders")
@Presentation
public class OrderUpdateMajorInterestApi {

	private final OrderUpdateMajorInterestUseCase orderUpdateMajorInterestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@RequestBody OrderUpdateMajorInterestRequest request) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		orderUpdateMajorInterestUseCase.update(sessionMember.getId(), request);
		return ResponseEntity.noContent().build();
	}
}
