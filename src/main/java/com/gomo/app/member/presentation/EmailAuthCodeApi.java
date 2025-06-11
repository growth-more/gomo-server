package com.gomo.app.member.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.member.application.CreateEmailAuthCodeUseCase;
import com.gomo.app.member.application.VerifyEmailAuthCodeUseCase;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/emails/codes/auth")
@Presentation
public class EmailAuthCodeApi {

	private final CreateEmailAuthCodeUseCase createEmailAuthCodeUseCase;
	private final VerifyEmailAuthCodeUseCase verifyEmailAuthCodeUseCase;

	@PostMapping
	public ResponseEntity<Void> createEmailAuthCode(@RequestBody CreateEmailAuthCodeRequest request) {
		createEmailAuthCodeUseCase.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<Void> verifyEmailAuthCode(@ModelAttribute VerifyEmailAuthCodeRequest request) {
		verifyEmailAuthCodeUseCase.verify(request);
		return ResponseEntity.ok().build();
	}
}
