package com.gomo.app.core.quest.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.quest.adapter.in.api.request.OrderUpdateRepeatQuestRequest;
import com.gomo.app.core.quest.application.port.in.RepeatQuestOrderUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats/orders")
@CoreApi
public class OrderUpdateRepeatQuestApi {

	private final RepeatQuestOrderUpdater repeatQuestOrderUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody OrderUpdateRepeatQuestRequest request) {
		repeatQuestOrderUpdater.update(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
