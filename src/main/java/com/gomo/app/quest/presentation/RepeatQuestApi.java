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
import com.gomo.app.quest.application.CreateRepeatQuestUseCase;
import com.gomo.app.quest.application.DeleteRepeatQuestUseCase;
import com.gomo.app.quest.application.ReadRepeatQuestUseCase;
import com.gomo.app.quest.application.UpdateRepeatQuestUseCase;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;
import com.gomo.app.quest.presentation.response.ListRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats")
@Presentation
public class RepeatQuestApi {

	private final CreateRepeatQuestUseCase createRepeatQuestUseCase;
	private final ReadRepeatQuestUseCase readRepeatQuestUseCase;
	private final UpdateRepeatQuestUseCase updateRepeatQuestUseCase;
	private final DeleteRepeatQuestUseCase deleteRepeatQuestUseCase;

	@PostMapping
	public ResponseEntity<CreateRepeatQuestResponse> create(@RequestBody CreateRepeatQuestRequest request) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		CreateRepeatQuestResponse response = createRepeatQuestUseCase.create(ParticipantId.of(sessionMember.getId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ListRepeatQuestResponse> findAll() {
		SessionMember sessionMember = MemberContext.getSessionMember();
		ListRepeatQuestResponse response = readRepeatQuestUseCase.findAll(ParticipantId.of(sessionMember.getId()));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable("id") UUID repeatQuestId, @RequestBody UpdateRepeatQuestRequest request) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		updateRepeatQuestUseCase.update(sessionMember.getId(), RepeatQuestId.of(repeatQuestId), request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") UUID repeatQuestId) {
		SessionMember sessionMember = MemberContext.getSessionMember();
		deleteRepeatQuestUseCase.delete(sessionMember.getId(), RepeatQuestId.of(repeatQuestId));
		return ResponseEntity.noContent().build();
	}
}
