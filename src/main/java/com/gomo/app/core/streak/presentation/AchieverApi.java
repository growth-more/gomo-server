package com.gomo.app.core.streak.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.core.streak.application.ReadAchieverUseCase;
import com.gomo.app.core.streak.presentation.response.ReadAchieverResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/achievers")
@Presentation
public class AchieverApi {

	private final ReadAchieverUseCase readAchieverUseCase;

	@GetMapping
	public ResponseEntity<ReadAchieverResponse> find(@Auth AuthInfo authInfo) {
		ReadAchieverResponse response = readAchieverUseCase.find(authInfo.getMemberId());
		return ResponseEntity.ok(response);
	}
}
