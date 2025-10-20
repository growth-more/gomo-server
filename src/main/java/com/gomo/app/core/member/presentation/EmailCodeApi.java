package com.gomo.app.core.member.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.application.usecase.CreateEmailCodeUseCase;
import com.gomo.app.core.member.application.usecase.VerifyEmailCodeUseCase;
import com.gomo.app.core.member.presentation.request.CreateEmailCodeRequest;
import com.gomo.app.core.member.presentation.request.VerifyEmailCodeRequest;
import com.gomo.app.core.member.presentation.response.CreateEmailCodeResponse;
import com.gomo.app.core.member.presentation.response.VerifyEmailCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/emails/codes")
@CoreApi
public class EmailCodeApi {

	private final CreateEmailCodeUseCase createEmailCodeUseCase;
	private final VerifyEmailCodeUseCase verifyEmailCodeUseCase;

	@PostMapping("/signup")
	public ResponseEntity<CreateEmailCodeResponse> create(@RequestBody CreateEmailCodeRequest request) {
		String emailCode = createEmailCodeUseCase.createForSignUp(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(CreateEmailCodeResponse.of(emailCode));
	}

	@PostMapping("/passwords/reset")
	public ResponseEntity<CreateEmailCodeResponse> createForPassword(@RequestBody CreateEmailCodeRequest request) {
		String emailCode = createEmailCodeUseCase.createForPasswordReset(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(CreateEmailCodeResponse.of(emailCode));
	}

	@GetMapping("/verify")
	public ResponseEntity<VerifyEmailCodeResponse> verify(@ModelAttribute VerifyEmailCodeRequest request) {
		String temporaryToken = verifyEmailCodeUseCase.verify(request.getEmail(), request.getCode());
		return ResponseEntity.status(HttpStatus.OK).body(VerifyEmailCodeResponse.of(temporaryToken));
	}
}
