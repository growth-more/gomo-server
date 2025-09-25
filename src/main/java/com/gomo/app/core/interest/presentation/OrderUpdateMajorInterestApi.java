package com.gomo.app.core.interest.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.interest.application.OrderUpdateMajorInterestUseCase;
import com.gomo.app.core.interest.presentation.request.OrderUpdateMajorInterestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/majors/orders")
@Presentation
public class OrderUpdateMajorInterestApi {

	private final OrderUpdateMajorInterestUseCase orderUpdateMajorInterestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateMajorInterestRequest request) {
		orderUpdateMajorInterestUseCase.update(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.noContent().build();
	}
}
