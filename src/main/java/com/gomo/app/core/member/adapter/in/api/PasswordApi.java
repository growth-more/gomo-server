package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.ResetPasswordRequest;
import com.gomo.app.core.member.adapter.in.api.request.UpdatePasswordRequest;
import com.gomo.app.core.member.application.port.in.PasswordResetter;
import com.gomo.app.core.member.application.port.in.PasswordUpdater;
import com.gomo.app.core.auth.adapter.in.security.Auth;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/passwords")
@CoreApi
public class PasswordApi {

	private final PasswordUpdater passwordUpdater;
	private final PasswordResetter passwordResetter;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdatePasswordRequest request) {
		passwordUpdater.update(authInfo.getPrincipalId(), request.getOriginPassword(), request.getNewPassword());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/reset")
	public ResponseEntity<Void> reset(@RequestBody ResetPasswordRequest request) {
		passwordResetter.reset(request.getEmail(), request.getNewPassword(), request.getTemporaryToken());
		return ResponseEntity.noContent().build();
	}
}
