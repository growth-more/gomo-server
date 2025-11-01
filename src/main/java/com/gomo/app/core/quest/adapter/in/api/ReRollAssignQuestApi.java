package com.gomo.app.core.quest.adapter.in.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.quest.adapter.in.api.request.ReRollAssignQuestRequest;
import com.gomo.app.core.quest.adapter.in.api.response.ReadAssignQuestDetailResponse;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDetailDto;
import com.gomo.app.core.quest.application.port.in.AssignQuestReRoller;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/quests/assigns/re-roll")
@CoreApi
public class ReRollAssignQuestApi {

	private final AssignQuestReRoller assignQuestReRoller;

	@PostMapping
	public ResponseEntity<ReadAssignQuestDetailResponse> reRoll(@Auth AuthInfo authInfo, @RequestBody ReRollAssignQuestRequest request) {
		AssignQuestDetailDto assignQuestDetailDto = assignQuestReRoller.reRoll(authInfo.getPrincipalId(), request.getAssignQuestId());
		return ResponseEntity.status(CREATED).body(ReadAssignQuestDetailResponse.from(assignQuestDetailDto));
	}
}
