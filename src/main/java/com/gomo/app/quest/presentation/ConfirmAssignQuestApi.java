package com.gomo.app.quest.presentation;

import java.util.UUID;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.ConfirmAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/confirm")
@Presentation
public class ConfirmAssignQuestApi {

	private final ConfirmAssignQuestUseCase confirmAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> confirm(@Auth MemberId memberId, @PathVariable("id") UUID assignQuestId) {
		confirmAssignQuestUseCase.confirm(memberId.getId(), AssignQuestId.of(assignQuestId));
		return ResponseEntity.noContent().build();
	}
}
