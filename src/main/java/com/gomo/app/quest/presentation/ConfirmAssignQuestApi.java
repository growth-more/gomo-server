package com.gomo.app.quest.presentation;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.quest.application.ConfirmAssignQuestUseCase;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/confirm")
@Presentation
public class ConfirmAssignQuestApi {

	private final ConfirmAssignQuestUseCase confirmAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> confirm(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId) {
		confirmAssignQuestUseCase.confirm(authInfo.getMemberId(), assignQuestId);
		return ResponseEntity.noContent().build();
	}
}
