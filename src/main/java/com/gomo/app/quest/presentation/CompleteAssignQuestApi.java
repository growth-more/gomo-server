package com.gomo.app.quest.presentation;

import java.util.UUID;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.CompleteAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.presentation.request.CompleteAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/{id}/complete")
@Presentation
public class CompleteAssignQuestApi {

	private final CompleteAssignQuestUseCase completeAssignQuestUseCase;

	@PutMapping
	public ResponseEntity<Void> complete(@Auth MemberId memberId, @PathVariable("id") UUID assignQuestId, @RequestBody CompleteAssignQuestRequest request) {
		completeAssignQuestUseCase.complete(memberId.getId(), AssignQuestId.of(assignQuestId), request);
		return ResponseEntity.noContent().build();
	}
}
