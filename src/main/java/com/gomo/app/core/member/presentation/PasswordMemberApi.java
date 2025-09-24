package com.gomo.app.core.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.core.member.application.ResetPasswordUseCase;
import com.gomo.app.core.member.application.UpdatePasswordUseCase;
import com.gomo.app.core.member.presentation.request.ResetPasswordRequest;
import com.gomo.app.core.member.presentation.request.UpdatePasswordRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/passwords")
@Presentation
public class PasswordMemberApi {

	private final UpdatePasswordUseCase updatePasswordUseCase;
	private final ResetPasswordUseCase resetPasswordUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdatePasswordRequest request) {
		updatePasswordUseCase.update(authInfo.getMemberId(), request.getOriginPassword(), request.getNewPassword());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/reset")
	public ResponseEntity<Void> reset(@RequestBody ResetPasswordRequest request) {
		resetPasswordUseCase.reset(request.getEmail(), request.getNewPassword());
		return ResponseEntity.noContent().build();
	}
}
