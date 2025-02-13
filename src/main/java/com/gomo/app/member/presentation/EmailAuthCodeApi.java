package com.gomo.app.member.presentation;

import com.gomo.app.member.application.VerifyEmailAuthCodeUseCase;
import com.gomo.app.member.domain.service.MemberValidator;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.VerifyEmailAuthCodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	private final VerifyEmailAuthCodeUseCase verifyEmailAuthCodeUseCase;

	@PostMapping
	public ResponseEntity<CreateEmailAuthCodeResponse> create(@RequestBody CreateEmailAuthCodeRequest request) {
		CreateEmailAuthCodeResponse response = createEmailAuthCodeUseCase.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<VerifyEmailAuthCodeResponse> validate(@ModelAttribute VerifyEmailAuthCodeRequest request){
		VerifyEmailAuthCodeResponse response = verifyEmailAuthCodeUseCase.verify(request);
		return ResponseEntity.ok().body(response);
	}
}
