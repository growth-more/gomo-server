package com.gomo.app.support.auth.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.support.auth.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.support.auth.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.common.Presentation;
import com.gomo.app.core.member.application.CreateEmailAuthCodeUseCase;
import com.gomo.app.core.member.application.VerifyEmailAuthCodeUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth/codes")
@Presentation
public class EmailAuthCodeApi {

	private final CreateEmailAuthCodeUseCase createEmailAuthCodeUseCase;
	private final VerifyEmailAuthCodeUseCase verifyEmailAuthCodeUseCase;

	@PostMapping("/generate/emails")
	public ResponseEntity<Void> createEmailAuthCode(@RequestBody CreateEmailAuthCodeRequest request) {
		createEmailAuthCodeUseCase.createForSignUp(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/generate/passwords")
	public ResponseEntity<Void> createPasswordAuthCode(@RequestBody CreateEmailAuthCodeRequest request) {
		createEmailAuthCodeUseCase.createForPasswordReset(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/verify")
	public ResponseEntity<Void> verifyEmailAuthCode(@ModelAttribute VerifyEmailAuthCodeRequest request) {
		verifyEmailAuthCodeUseCase.verify(request.getEmail(), request.getCode());
		return ResponseEntity.ok().build();
	}
}
