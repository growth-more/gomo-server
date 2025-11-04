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
import com.gomo.app.core.quest.adapter.in.api.request.CreateAssignQuestRequest;
import com.gomo.app.core.quest.adapter.in.api.request.UpdateAssignQuestRequest;
import com.gomo.app.core.quest.adapter.in.api.response.CreateAssignQuestResponse;
import com.gomo.app.core.quest.adapter.in.api.response.ListAssignQuestDetailResponse;
import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDetailDto;
import com.gomo.app.core.quest.application.port.in.AssignQuestCreator;
import com.gomo.app.core.quest.application.port.in.AssignQuestDeleter;
import com.gomo.app.core.quest.application.port.in.AssignQuestDetailReader;
import com.gomo.app.core.quest.application.port.in.AssignQuestUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns")
@CoreApi
public class AssignQuestApi {

	private final AssignQuestCreator assignQuestCreator;
	private final AssignQuestDetailReader assignQuestDetailReader;
	private final AssignQuestUpdater assignQuestUpdater;
	private final AssignQuestDeleter assignQuestDeleter;

	@PostMapping
	public ResponseEntity<CreateAssignQuestResponse> create(@Session SessionInfo sessionInfo, @RequestBody CreateAssignQuestRequest request) {
		UUID assignQuestId = assignQuestCreator.create(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.status(CREATED).body(CreateAssignQuestResponse.of(assignQuestId));
	}

	@GetMapping
	public ResponseEntity<ListAssignQuestDetailResponse> findAll(@Session SessionInfo sessionInfo) {
		ListAssignQuestDetailDto dto = assignQuestDetailReader.readAll(sessionInfo.getPrincipalId());
		return ResponseEntity.ok(ListAssignQuestDetailResponse.from(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @PathVariable("id") UUID assignQuestId, @RequestBody UpdateAssignQuestRequest request) {
		assignQuestUpdater.update(request.toCommand(sessionInfo.getPrincipalId(), assignQuestId));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Session SessionInfo sessionInfo, @PathVariable("id") UUID assignQuestId) {
		assignQuestDeleter.delete(sessionInfo.getPrincipalId(), assignQuestId);
		return ResponseEntity.noContent().build();
	}
}
