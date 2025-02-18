package com.gomo.app.interest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.interest.application.OrderUpdateMajorInterestUseCase;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/interests/majors/orders")
@Presentation
public class OrderUpdateMajorInterestApi {

	private final OrderUpdateMajorInterestUseCase orderUpdateMajorInterestUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody OrderUpdateMajorInterestRequest request) {
		orderUpdateMajorInterestUseCase.update(authInfo.getMemberId(), request);
		return ResponseEntity.noContent().build();
	}
}
