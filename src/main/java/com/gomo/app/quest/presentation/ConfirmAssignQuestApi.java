package com.gomo.app.quest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.Presentation;
import com.gomo.app.quest.application.ConfirmAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/confirm")
@Presentation
public class ConfirmAssignQuestApi {

	private final ConfirmAssignQuestUseCase confirmAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> confirm(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId) {
		confirmAssignQuestUseCase.confirm(authInfo.getMemberId(), AssignQuestId.of(assignQuestId));
		return ResponseEntity.noContent().build();
	}
}
