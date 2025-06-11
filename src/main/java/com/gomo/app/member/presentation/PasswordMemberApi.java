package com.gomo.app.member.presentation;

import com.gomo.app.member.application.UpdatePasswordUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.member.application.UpdateMemberUseCase;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/passwords")
@Presentation
public class PasswordMemberApi {

	private final UpdatePasswordUseCase updatePasswordUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdatePasswordRequest request) {
		updatePasswordUseCase.update(authInfo.getMemberId(), request);
		return ResponseEntity.noContent().build();
	}
}
