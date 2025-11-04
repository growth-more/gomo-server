package com.gomo.app.core.quest.adapter.in.api;

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

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.quest.adapter.in.api.request.CreateRepeatQuestRequest;
import com.gomo.app.core.quest.adapter.in.api.request.UpdateRepeatQuestRequest;
import com.gomo.app.core.quest.adapter.in.api.response.CreateRepeatQuestResponse;
import com.gomo.app.core.quest.adapter.in.api.response.ListRepeatQuestResponse;
import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;
import com.gomo.app.core.quest.application.port.in.RepeatQuestCreator;
import com.gomo.app.core.quest.application.port.in.RepeatQuestDeleter;
import com.gomo.app.core.quest.application.port.in.RepeatQuestReader;
import com.gomo.app.core.quest.application.port.in.RepeatQuestUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/repeats")
@CoreApi
public class RepeatQuestApi {

	private final RepeatQuestCreator repeatQuestCreator;
	private final RepeatQuestReader repeatQuestReader;
	private final RepeatQuestUpdater repeatQuestUpdater;
	private final RepeatQuestDeleter repeatQuestDeleter;

	@PostMapping
	public ResponseEntity<CreateRepeatQuestResponse> create(@Session SessionInfo sessionInfo, @RequestBody CreateRepeatQuestRequest request) {
		UUID repeatQuestId = repeatQuestCreator.create(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.status(CREATED).body(CreateRepeatQuestResponse.of(repeatQuestId));
	}

	@GetMapping
	public ResponseEntity<ListRepeatQuestResponse> findAll(@Session SessionInfo sessionInfo) {
		ListRepeatQuestDto dto = repeatQuestReader.readAll(sessionInfo.getPrincipalId());
		return ResponseEntity.ok(ListRepeatQuestResponse.from(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @PathVariable("id") UUID repeatQuestId, @RequestBody UpdateRepeatQuestRequest request) {
		repeatQuestUpdater.update(request.toCommand(sessionInfo.getPrincipalId(), repeatQuestId));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Session SessionInfo sessionInfo, @PathVariable("id") UUID repeatQuestId) {
		repeatQuestDeleter.delete(sessionInfo.getPrincipalId(), repeatQuestId);
		return ResponseEntity.noContent().build();
	}
}
