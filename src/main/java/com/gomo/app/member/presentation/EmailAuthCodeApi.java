package com.gomo.app.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.CreateEmailAuthCodeUseCase;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.CreateEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/emails/codes/auth")
@Presentation
public class EmailAuthCodeApi {

	private final CreateEmailAuthCodeUseCase createEmailAuthCodeUseCase;

	@PostMapping
	public ResponseEntity<CreateEmailAuthCodeResponse> create(@RequestBody CreateEmailAuthCodeRequest request) {
		return null;
	}
}
