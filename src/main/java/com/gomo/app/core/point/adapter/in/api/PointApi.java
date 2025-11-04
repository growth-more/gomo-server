package com.gomo.app.core.point.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.common.web.PageRequest;
import com.gomo.app.core.point.adapter.in.api.response.ListPointResponse;
import com.gomo.app.core.point.adapter.in.api.response.ReadBalanceResponse;
import com.gomo.app.core.point.application.port.dto.ListPointDto;
import com.gomo.app.core.point.application.port.in.BalanceReader;
import com.gomo.app.core.point.application.port.in.PointReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/points")
@CoreApi
public class PointApi {

	private final PointReader pointReader;
	private final BalanceReader balanceReader;

	@GetMapping
	public ResponseEntity<ListPointResponse> findAll(@Session SessionInfo sessionInfo, @ModelAttribute PageRequest pageRequest) {
		ListPointDto dto = pointReader.readAll(sessionInfo.getPrincipalId(), pageRequest);
		return ResponseEntity.ok(ListPointResponse.from(dto));
	}

	@GetMapping("/balances")
	public ResponseEntity<ReadBalanceResponse> findBalance(@Session SessionInfo sessionInfo) {
		int balance = balanceReader.read(sessionInfo.getPrincipalId());
		return ResponseEntity.ok(ReadBalanceResponse.of(balance));
	}
}
