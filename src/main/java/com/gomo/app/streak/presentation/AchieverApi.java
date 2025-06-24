package com.gomo.app.streak.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.streak.application.ReadAchieverUseCase;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.presentation.response.ReadAchieverResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/achievers")
@Presentation
public class AchieverApi {

	private final ReadAchieverUseCase readAchieverUseCase;

	@GetMapping
	public ResponseEntity<ReadAchieverResponse> find(@Auth AuthInfo authInfo) {
		ReadAchieverResponse response = readAchieverUseCase.find(AchieverId.of(authInfo.getMemberId()));
		return ResponseEntity.ok(response);
	}
}
