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

import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.quest.application.CreateRepeatQuestUseCase;
import com.gomo.app.quest.application.DeleteRepeatQuestUseCase;
import com.gomo.app.quest.application.ReadRepeatQuestUseCase;
import com.gomo.app.quest.application.UpdateRepeatQuestUseCase;
import com.gomo.app.quest.application.port.dto.CreateRepeatQuestDto;
import com.gomo.app.quest.application.port.dto.ListRepeatQuestDto;
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
	public ResponseEntity<CreateRepeatQuestResponse> create(@Auth AuthInfo authInfo, @RequestBody CreateRepeatQuestRequest request) {
		CreateRepeatQuestDto dto = createRepeatQuestUseCase.create(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.status(CREATED).body(CreateRepeatQuestResponse.of(dto.id()));
	}

	@GetMapping
	public ResponseEntity<ListRepeatQuestResponse> findAll(@Auth AuthInfo authInfo) {
		ListRepeatQuestDto dto = readRepeatQuestUseCase.findAll(authInfo.getMemberId());
		return ResponseEntity.ok(ListRepeatQuestResponse.from(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @PathVariable("id") UUID repeatQuestId, @RequestBody UpdateRepeatQuestRequest request) {
		updateRepeatQuestUseCase.update(request.toCommand(authInfo.getMemberId(), repeatQuestId));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID repeatQuestId) {
		deleteRepeatQuestUseCase.delete(authInfo.getMemberId(), repeatQuestId);
		return ResponseEntity.noContent().build();
	}
}
