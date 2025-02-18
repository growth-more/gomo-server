package com.gomo.app.quest.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

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
		CreateAssignQuestResponse response = createAssignQuestUseCase.create(ParticipantId.of(authInfo.getMemberId()), request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<ListAssignQuestResponse> findAll(@Auth AuthInfo authInfo) {
		ListAssignQuestResponse response = readAssignQuestUseCase.findAll(ParticipantId.of(authInfo.getMemberId()));
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId, @RequestBody UpdateAssignQuestRequest request) {
		updateAssignQuestUseCase.update(authInfo.getMemberId(), AssignQuestId.of(assignQuestId), request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Auth AuthInfo authInfo, @PathVariable("id") UUID assignQuestId) {
		deleteAssignQuestUseCase.delete(authInfo.getMemberId(), AssignQuestId.of(assignQuestId));
		return ResponseEntity.noContent().build();
	}
}
