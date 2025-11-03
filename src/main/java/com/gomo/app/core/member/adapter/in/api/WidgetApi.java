package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.UpdateWidgetRequest;
import com.gomo.app.core.member.application.port.in.WidgetUpdater;
import com.gomo.app.core.auth.adapter.in.security.Auth;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/widgets")
@CoreApi
public class WidgetApi {

	private final WidgetUpdater widgetUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateWidgetRequest request) {
		widgetUpdater.update(authInfo.getPrincipalId(), request.getSnapshot());
		return ResponseEntity.noContent().build();
	}
}
