package com.gomo.app.quest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.quest.application.CreateAssignQuestUseCase;
import com.gomo.app.quest.application.DeleteAssignQuestUseCase;
import com.gomo.app.quest.application.ReadAssignQuestUseCase;
import com.gomo.app.quest.application.UpdateAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;
import com.gomo.app.quest.presentation.response.ListAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns")
@Presentation
public class AssignQuestApi {

	private final CreateAssignQuestUseCase createAssignQuestUseCase;
	private final ReadAssignQuestUseCase readAssignQuestUseCase;
	private final UpdateAssignQuestUseCase updateAssignQuestUseCase;
	private final DeleteAssignQuestUseCase deleteAssignQuestUseCase;

	@PostMapping
	public ResponseEntity<CreateAssignQuestResponse> create(@RequestBody CreateAssignQuestRequest request) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		CreateAssignQuestResponse response = createAssignQuestUseCase.create(ParticipantId.of(sessionMember.getId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ListAssignQuestResponse> findAll() {
		SessionMember sessionMember = MemberContext.getSessionMember();
		ListAssignQuestResponse response = readAssignQuestUseCase.findAll(ParticipantId.of(sessionMember.getId()));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") UUID assignQuestId, @RequestBody UpdateAssignQuestRequest request) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		updateAssignQuestUseCase.update(sessionMember.getId(), AssignQuestId.of(assignQuestId), request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") UUID assignQuestId) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		deleteAssignQuestUseCase.delete(sessionMember.getId(), AssignQuestId.of(assignQuestId));
		return ResponseEntity.noContent().build();
	}
}
