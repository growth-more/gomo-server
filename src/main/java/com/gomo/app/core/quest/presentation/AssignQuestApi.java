package com.gomo.app.core.quest.presentation;

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

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDto;
import com.gomo.app.core.quest.application.usecase.CreateAssignQuestUseCase;
import com.gomo.app.core.quest.application.usecase.DeleteAssignQuestUseCase;
import com.gomo.app.core.quest.application.usecase.ReadAssignQuestUseCase;
import com.gomo.app.core.quest.application.usecase.UpdateAssignQuestUseCase;
import com.gomo.app.core.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.core.quest.presentation.request.UpdateAssignQuestRequest;
import com.gomo.app.core.quest.presentation.response.CreateAssignQuestResponse;
import com.gomo.app.core.quest.presentation.response.ListAssignQuestResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

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
	public ResponseEntity<CreateAssignQuestResponse> create(@Auth AuthInfo authInfo, @RequestBody CreateAssignQuestRequest request) {
		UUID assignQuestId = createAssignQuestUseCase.create(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.status(CREATED).body(CreateAssignQuestResponse.of(assignQuestId));
	}

	@GetMapping
	public ResponseEntity<ListAssignQuestResponse> findAll(@Auth AuthInfo authInfo) {
		ListAssignQuestDto dto = readAssignQuestUseCase.findAll(authInfo.getMemberId());
		return ResponseEntity.ok(ListAssignQuestResponse.from(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId, @RequestBody UpdateAssignQuestRequest request) {
		updateAssignQuestUseCase.update(request.toCommand(authInfo.getMemberId(), assignQuestId));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId) {
		deleteAssignQuestUseCase.delete(authInfo.getMemberId(), assignQuestId);
		return ResponseEntity.noContent().build();
	}
}
