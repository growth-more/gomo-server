package com.gomo.app.core.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.application.usecase.UpdateWidgetUseCase;
import com.gomo.app.core.member.presentation.request.UpdateWidgetRequest;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/widgets")
@CoreApi
public class WidgetApi {

	private final UpdateWidgetUseCase updateWidgetUseCase;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateWidgetRequest request) {
		updateWidgetUseCase.update(authInfo.getMemberId(), request.getSnapshot());
		return ResponseEntity.noContent().build();
	}
}
