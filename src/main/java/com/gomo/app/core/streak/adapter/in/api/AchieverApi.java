package com.gomo.app.core.streak.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.streak.adapter.in.api.response.ReadAchieverResponse;
import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.application.port.in.AchieverReader;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/achievers")
@CoreApi
public class AchieverApi {

	private final AchieverReader achieverReader;

	@GetMapping
	public ResponseEntity<ReadAchieverResponse> find(@Auth AuthInfo authInfo) {
		AchieverDto achieverDto = achieverReader.read(authInfo.getPrincipalId());
		return ResponseEntity.ok(ReadAchieverResponse.from(achieverDto));
	}
}
