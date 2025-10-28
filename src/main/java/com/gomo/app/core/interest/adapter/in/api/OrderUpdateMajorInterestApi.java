package com.gomo.app.core.interest.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.interest.adapter.in.api.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.core.interest.application.port.in.OrderUpdateMajorInterestUseCase;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/majors/orders")
@CoreApi
public class OrderUpdateMajorInterestApi {

	private final OrderUpdateMajorInterestUseCase orderUpdateMajorInterestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateMajorInterestRequest request) {
		orderUpdateMajorInterestUseCase.update(request.toCommand(authInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
