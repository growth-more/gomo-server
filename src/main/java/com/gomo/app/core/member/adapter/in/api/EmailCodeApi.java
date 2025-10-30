package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.CreateEmailCodeRequest;
import com.gomo.app.core.member.adapter.in.api.request.VerifyEmailCodeRequest;
import com.gomo.app.core.member.adapter.in.api.response.VerifyEmailCodeResponse;
import com.gomo.app.core.member.application.port.in.EmailCodeIssuer;
import com.gomo.app.core.member.application.port.in.EmailTokenIssuer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/emails/codes")
@CoreApi
public class EmailCodeApi {

	private final EmailCodeIssuer emailCodeIssuer;
	private final EmailTokenIssuer emailTokenIssuer;

	@PostMapping("/signup")
	public ResponseEntity<Void> create(@RequestBody CreateEmailCodeRequest request) {
		emailCodeIssuer.issueForSignUp(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/passwords/reset")
	public ResponseEntity<Void> createForPassword(@RequestBody CreateEmailCodeRequest request) {
		emailCodeIssuer.issueForPasswordReset(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/verify")
	public ResponseEntity<VerifyEmailCodeResponse> verify(@ModelAttribute VerifyEmailCodeRequest request) {
		String temporaryToken = emailTokenIssuer.issue(request.getEmail(), request.getCode());
		return ResponseEntity.status(HttpStatus.OK).body(VerifyEmailCodeResponse.of(temporaryToken));
	}
}
