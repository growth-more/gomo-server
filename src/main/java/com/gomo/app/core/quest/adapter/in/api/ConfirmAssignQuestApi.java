package com.gomo.app.core.quest.adapter.in.api;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.quest.application.port.in.AssignQuestConfirmer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/confirm")
@CoreApi
public class ConfirmAssignQuestApi {

	private final AssignQuestConfirmer assignQuestConfirmer;

	@PutMapping
	public ResponseEntity<Void> confirm(@Session SessionInfo sessionInfo, @PathVariable("id") UUID assignQuestId) {
		assignQuestConfirmer.confirm(sessionInfo.getPrincipalId(), assignQuestId);
		return ResponseEntity.noContent().build();
	}
}
