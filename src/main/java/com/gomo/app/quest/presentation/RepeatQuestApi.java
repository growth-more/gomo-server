package com.gomo.app.quest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.Presentation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats")
@Presentation
public class RepeatQuestApi {

	private final CreateRepeatQuestUseCase createRepeatQuestUseCase;
	private final ReadRepeatQuestUseCase readRepeatQuestUseCase;
	private final UpdateRepeatQuestUseCase updateRepeatQuestUseCase;
	private final DeleteRepeatQuestUseCase deleteRepeatQuestUseCase;

	@PostMapping
	public ResponseEntity<CreateRepeatQuestResponse> create(@Auth AuthInfo authInfo, @RequestBody CreateRepeatQuestRequest request) {
		CreateRepeatQuestResponse response = createRepeatQuestUseCase.create(ParticipantId.of(authInfo.getMemberId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ListRepeatQuestResponse> findAll(@Auth AuthInfo authInfo) {
		ListRepeatQuestResponse response = readRepeatQuestUseCase.findAll(ParticipantId.of(authInfo.getMemberId()));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @PathVariable("id") UUID repeatQuestId, @RequestBody UpdateRepeatQuestRequest request) {
		updateRepeatQuestUseCase.update(authInfo.getMemberId(), RepeatQuestId.of(repeatQuestId), request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID repeatQuestId) {
		deleteRepeatQuestUseCase.delete(authInfo.getMemberId(), RepeatQuestId.of(repeatQuestId));
		return ResponseEntity.noContent().build();
	}
}
