package com.gomo.app.core.quest.adapter.in.api;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.application.port.in.AssignQuestConfirmer;
import com.gomo.app.support.auth.adapter.in.security.Auth;
import com.gomo.app.support.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/confirm")
@CoreApi
public class ConfirmAssignQuestApi {

	private final AssignQuestConfirmer assignQuestConfirmer;

	@PutMapping
	public ResponseEntity<Void> confirm(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId) {
		assignQuestConfirmer.confirm(authInfo.getPrincipalId(), assignQuestId);
		return ResponseEntity.noContent().build();
	}
}
