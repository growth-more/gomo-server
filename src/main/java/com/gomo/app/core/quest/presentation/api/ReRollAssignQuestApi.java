package com.gomo.app.core.quest.presentation.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.application.usecase.ReRollAssignQuestUseCase;
import com.gomo.app.core.quest.presentation.api.request.ReRollAssignQuestRequest;
import com.gomo.app.core.quest.presentation.api.response.ReadAssignQuestResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/re-roll")
@CoreApi
public class ReRollAssignQuestApi {

	private final ReRollAssignQuestUseCase reRollAssignQuestUseCase;

	@PostMapping
	public ResponseEntity<ReadAssignQuestResponse> reRoll(@Auth AuthInfo authInfo, @RequestBody ReRollAssignQuestRequest request) {
		AssignQuestDto assignQuestDto = reRollAssignQuestUseCase.reRoll(authInfo.getPrincipalId(), request.getAssignQuestId());
		return ResponseEntity.status(CREATED).body(ReadAssignQuestResponse.from(assignQuestDto));
	}
}
