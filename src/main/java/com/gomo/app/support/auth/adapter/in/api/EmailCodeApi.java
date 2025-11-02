package com.gomo.app.support.auth.adapter.in.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.support.auth.adapter.in.api.request.CreateEmailCodeRequest;
import com.gomo.app.support.auth.adapter.in.api.request.VerifyEmailCodeRequest;
import com.gomo.app.support.auth.adapter.in.api.response.VerifyEmailCodeResponse;
import com.gomo.app.support.auth.application.port.in.AuthCodeIssuer;
import com.gomo.app.support.auth.application.port.in.AuthTokenIssuer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth/codes/emails")
@CoreApi
public class EmailCodeApi {

	private final AuthCodeIssuer authCodeIssuer;
	private final AuthTokenIssuer authTokenIssuer;

	@PostMapping("/signup")
	public ResponseEntity<Void> create(@RequestBody CreateEmailCodeRequest request) {
		authCodeIssuer.issueForSignUp(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/passwords/reset")
	public ResponseEntity<Void> createForPassword(@RequestBody CreateEmailCodeRequest request) {
		authCodeIssuer.issueForPasswordReset(request.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/verify")
	public ResponseEntity<VerifyEmailCodeResponse> verify(@RequestBody VerifyEmailCodeRequest request) {
		String temporaryToken = authTokenIssuer.issue(request.getEmail(), request.getCode());
		return ResponseEntity.status(HttpStatus.OK).body(VerifyEmailCodeResponse.of(temporaryToken));
	}
}
