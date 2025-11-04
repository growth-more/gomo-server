package com.gomo.app.core.streak.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.streak.adapter.in.api.response.ReadAchieverResponse;
import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.application.port.in.AchieverReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/achievers")
@CoreApi
public class AchieverApi {

	private final AchieverReader achieverReader;

	@GetMapping
	public ResponseEntity<ReadAchieverResponse> find(@Session SessionInfo sessionInfo) {
		AchieverDto achieverDto = achieverReader.read(sessionInfo.getPrincipalId());
		return ResponseEntity.ok(ReadAchieverResponse.from(achieverDto));
	}
}
