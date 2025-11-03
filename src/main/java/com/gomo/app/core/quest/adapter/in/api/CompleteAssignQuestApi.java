package com.gomo.app.core.quest.adapter.in.api;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.adapter.in.api.request.CompleteAssignQuestRequest;
import com.gomo.app.core.quest.application.port.in.AssignQuestCompleter;
import com.gomo.app.core.auth.adapter.in.security.Auth;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/complete")
@CoreApi
public class CompleteAssignQuestApi {

	private final AssignQuestCompleter assignQuestCompleter;

	@PutMapping
	public ResponseEntity<Void> complete(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId, @RequestBody CompleteAssignQuestRequest request) {
		assignQuestCompleter.complete(request.toCommand(authInfo.getPrincipalId(), assignQuestId, LocalDateTime.now()));
		return ResponseEntity.noContent().build();
	}
}
