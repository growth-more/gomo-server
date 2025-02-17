package com.gomo.app.quest.presentation;

import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.member.domain.model.MemberId;
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
	public ResponseEntity<CreateRepeatQuestResponse> create(@Auth MemberId memberId, @RequestBody CreateRepeatQuestRequest request) {
		CreateRepeatQuestResponse response = createRepeatQuestUseCase.create(ParticipantId.of(memberId.getId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ListRepeatQuestResponse> findAll(@Auth MemberId memberId) {
		ListRepeatQuestResponse response = readRepeatQuestUseCase.findAll(ParticipantId.of(memberId.getId()));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Auth MemberId memberId, @PathVariable("id") UUID repeatQuestId, @RequestBody UpdateRepeatQuestRequest request) {
		updateRepeatQuestUseCase.update(memberId.getId(), RepeatQuestId.of(repeatQuestId), request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Auth MemberId memberId, @PathVariable("id") UUID repeatQuestId) {
		deleteRepeatQuestUseCase.delete(memberId.getId(), RepeatQuestId.of(repeatQuestId));
		return ResponseEntity.noContent().build();
	}
}
