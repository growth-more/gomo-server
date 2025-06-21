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
import com.gomo.app.member.presentation.request.CreatePasswordAuthCodeRequest;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class EmailAuthCodeApi {

	private final CreateEmailAuthCodeUseCase createEmailAuthCodeUseCase;
	private final VerifyEmailAuthCodeUseCase verifyEmailAuthCodeUseCase;

	@PostMapping("/emails/codes/auth")
	public ResponseEntity<Void> createEmailAuthCode(@RequestBody CreateEmailAuthCodeRequest request) {
		createEmailAuthCodeUseCase.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/emails/codes/auth")
	public ResponseEntity<Void> verifyEmailAuthCode(@ModelAttribute VerifyEmailAuthCodeRequest request) {
		verifyEmailAuthCodeUseCase.verify(request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/passwords/codes/auth")
	public ResponseEntity<Void> createPasswordAuthCode(@RequestBody CreatePasswordAuthCodeRequest request) {
		createEmailAuthCodeUseCase.createPwReset(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/passwords/codes/auth")
	public ResponseEntity<Void> verifyPasswordAuthCode(@ModelAttribute VerifyEmailAuthCodeRequest request) {
		verifyEmailAuthCodeUseCase.verify(request);
		return ResponseEntity.ok().build();
	}
}
