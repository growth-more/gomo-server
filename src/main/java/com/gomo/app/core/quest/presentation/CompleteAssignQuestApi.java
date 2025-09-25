package com.gomo.app.core.quest.presentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;
import com.gomo.app.core.quest.application.usecase.CompleteAssignQuestUseCase;
import com.gomo.app.core.quest.presentation.request.CompleteAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/complete")
@Presentation
public class CompleteAssignQuestApi {

	private final CompleteAssignQuestUseCase completeAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> complete(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId, @RequestBody CompleteAssignQuestRequest request) {
		completeAssignQuestUseCase.complete(request.toCommand(authInfo.getMemberId(), assignQuestId));
		return ResponseEntity.noContent().build();
	}
}
