package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.member.adapter.in.api.request.UpdateWidgetRequest;
import com.gomo.app.core.member.application.port.in.WidgetUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/widgets")
@CoreApi
public class WidgetApi {

	private final WidgetUpdater widgetUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody UpdateWidgetRequest request) {
		widgetUpdater.update(sessionInfo.getPrincipalId(), request.getSnapshot());
		return ResponseEntity.noContent().build();
	}
}
