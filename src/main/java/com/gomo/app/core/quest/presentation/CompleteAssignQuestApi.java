package com.gomo.app.core.quest.presentation;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.application.usecase.CompleteAssignQuestUseCase;
import com.gomo.app.core.quest.presentation.request.CompleteAssignQuestRequest;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/complete")
@CoreApi
public class CompleteAssignQuestApi {

	private final CompleteAssignQuestUseCase completeAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> complete(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId, @RequestBody CompleteAssignQuestRequest request) {
		completeAssignQuestUseCase.complete(request.toCommand(authInfo.getMemberId(), assignQuestId, LocalDateTime.now()));
		return ResponseEntity.noContent().build();
	}
}
