package com.gomo.app.core.point.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.core.point.application.ReadBalanceUseCase;
import com.gomo.app.core.point.application.ReadPointUseCase;
import com.gomo.app.core.point.presentation.response.ListPointResponse;
import com.gomo.app.core.point.presentation.response.ReadBalanceResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/points")
@Presentation
public class PointApi {

	private final ReadPointUseCase readPointUseCase;
	private final ReadBalanceUseCase readBalanceUseCase;

	@GetMapping
	public ResponseEntity<ListPointResponse> findAll(@Auth AuthInfo authInfo, @ModelAttribute PageRequest pageRequest) {
		ListPointResponse response = readPointUseCase.findAll(authInfo.getMemberId(), pageRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/balances")
	public ResponseEntity<ReadBalanceResponse> findAll(@Auth AuthInfo authInfo) {
		ReadBalanceResponse response = readBalanceUseCase.find(authInfo.getMemberId());
		return ResponseEntity.ok(response);
	}
}
