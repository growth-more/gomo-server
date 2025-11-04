package com.gomo.app.core.quest.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.quest.adapter.in.api.request.OrderUpdateAssignQuestRequest;
import com.gomo.app.core.quest.application.port.in.AssignQuestOrderUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/orders")
@CoreApi
public class OrderUpdateAssignQuestApi {

	private final AssignQuestOrderUpdater assignQuestOrderUpdater;

	@PutMapping
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody OrderUpdateAssignQuestRequest request) {
		assignQuestOrderUpdater.update(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
