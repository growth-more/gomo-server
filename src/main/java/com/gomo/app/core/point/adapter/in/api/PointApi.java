package com.gomo.app.core.point.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.web.PageRequest;
import com.gomo.app.core.point.adapter.in.api.response.ListPointResponse;
import com.gomo.app.core.point.adapter.in.api.response.ReadBalanceResponse;
import com.gomo.app.core.point.application.port.dto.ListPointDto;
import com.gomo.app.core.point.application.port.in.BalanceReader;
import com.gomo.app.core.point.application.port.in.PointReader;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/points")
@CoreApi
public class PointApi {

	private final PointReader pointReader;
	private final BalanceReader balanceReader;

	@GetMapping
	public ResponseEntity<ListPointResponse> findAll(@Auth AuthInfo authInfo, @ModelAttribute PageRequest pageRequest) {
		ListPointDto dto = pointReader.readAll(authInfo.getPrincipalId(), pageRequest);
		return ResponseEntity.ok(ListPointResponse.from(dto));
	}

	@GetMapping("/balances")
	public ResponseEntity<ReadBalanceResponse> findBalance(@Auth AuthInfo authInfo) {
		int balance = balanceReader.read(authInfo.getPrincipalId());
		return ResponseEntity.ok(ReadBalanceResponse.of(balance));
	}
}
