package com.gomo.app.auth.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.auth.application.CreateEmailAuthCodeUseCase;
import com.gomo.app.auth.application.VerifyEmailAuthCodeUseCase;
import com.gomo.app.auth.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.auth.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.common.Presentation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/emails/codes/auth")
@Presentation
public class EmailAuthCodeApi {

	private final CreateEmailAuthCodeUseCase createEmailAuthCodeUseCase;
	private final VerifyEmailAuthCodeUseCase verifyEmailAuthCodeUseCase;

	@PostMapping
	public ResponseEntity<Void> createEmailAuthCode(CreateEmailAuthCodeRequest request) {
		createEmailAuthCodeUseCase.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<Void> verifyEmailAuthCode(VerifyEmailAuthCodeRequest request) {
		verifyEmailAuthCodeUseCase.verify(request);
		return ResponseEntity.ok().build();
	}
}
