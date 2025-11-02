package com.gomo.app.core.interest.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.interest.adapter.in.api.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.core.interest.application.port.in.MajorInterestOrderUpdater;
import com.gomo.app.support.auth.adapter.in.security.Auth;
import com.gomo.app.support.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/majors/orders")
@CoreApi
public class OrderUpdateMajorInterestApi {

	private final MajorInterestOrderUpdater majorInterestOrderUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateMajorInterestRequest request) {
		majorInterestOrderUpdater.update(request.toCommand(authInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
