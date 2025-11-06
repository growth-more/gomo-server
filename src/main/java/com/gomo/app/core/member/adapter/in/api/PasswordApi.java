package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.member.adapter.in.api.request.UpdatePasswordRequest;
import com.gomo.app.core.member.application.port.in.PasswordUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/passwords")
@CoreApi
public class PasswordApi {

	private final PasswordUpdater passwordUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody UpdatePasswordRequest request) {
		passwordUpdater.update(sessionInfo.getPrincipalId(), request.getOriginPassword(), request.getNewPassword());
		return ResponseEntity.noContent().build();
	}
}
