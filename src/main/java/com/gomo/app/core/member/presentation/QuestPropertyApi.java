package com.gomo.app.core.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.application.usecase.ReadQuestPropertyUseCase;
import com.gomo.app.core.member.application.usecase.UpdateQuestPropertyUseCase;
import com.gomo.app.core.member.presentation.request.UpdateQuestPropertyRequest;
import com.gomo.app.core.member.presentation.response.ReadQuestPropertyResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/properties/quests")
@CoreApi
public class QuestPropertyApi {

	private final ReadQuestPropertyUseCase readQuestPropertyUseCase;
	private final UpdateQuestPropertyUseCase updateQuestPropertyUseCase;

	@GetMapping
	public ResponseEntity<ReadQuestPropertyResponse> find(@Auth AuthInfo authInfo) {
		QuestPropertyDto dto = readQuestPropertyUseCase.find(authInfo.getPrincipalId());
		return ResponseEntity.ok(ReadQuestPropertyResponse.of(dto));
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateQuestPropertyRequest request) {
		updateQuestPropertyUseCase.update(request.toCommand(authInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
