package com.gomo.app.core.member.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.core.member.application.usecase.CreateEmailCodeUseCase;
import com.gomo.app.core.member.application.usecase.VerifyEmailCodeUseCase;
import com.gomo.app.core.member.presentation.request.CreateEmailCodeRequest;
import com.gomo.app.core.member.presentation.request.VerifyEmailCodeRequest;
import com.gomo.app.core.member.presentation.response.VerifyEmailCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/emails/codes")
@Presentation
public class EmailCodeApi {

	private final CreateEmailCodeUseCase createEmailCodeUseCase;
	private final VerifyEmailCodeUseCase verifyEmailCodeUseCase;

	@PostMapping("/signup")
	public ResponseEntity<Void> create(@RequestBody CreateEmailCodeRequest request) {
		createEmailCodeUseCase.createForSignUp(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/passwords/reset")
	public ResponseEntity<Void> createForPassword(@RequestBody CreateEmailCodeRequest request) {
		createEmailCodeUseCase.createForPasswordReset(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/verify")
	public ResponseEntity<VerifyEmailCodeResponse> verify(@ModelAttribute VerifyEmailCodeRequest request) {
		String temporaryToken = verifyEmailCodeUseCase.verify(request.getEmail(), request.getCode());
		return ResponseEntity.status(HttpStatus.OK).body(VerifyEmailCodeResponse.of(temporaryToken));
	}
}
