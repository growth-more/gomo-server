package com.gomo.app.core.interest.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.interest.adapter.in.api.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.core.interest.application.port.in.MajorInterestOrderUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/interests/majors/orders")
@CoreApi
public class OrderUpdateMajorInterestApi {

	private final MajorInterestOrderUpdater majorInterestOrderUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody OrderUpdateMajorInterestRequest request) {
		majorInterestOrderUpdater.update(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
