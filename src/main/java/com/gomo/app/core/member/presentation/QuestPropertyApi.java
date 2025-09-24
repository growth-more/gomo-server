package com.gomo.app.core.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;
import com.gomo.app.core.member.application.ReadQuestPropertyUseCase;
import com.gomo.app.core.member.application.UpdateQuestPropertyUseCase;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.presentation.request.UpdateQuestPropertyRequest;
import com.gomo.app.core.member.presentation.response.ReadQuestPropertyResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/properties/quests")
@Presentation
public class QuestPropertyApi {

	private final ReadQuestPropertyUseCase readQuestPropertyUseCase;
	private final UpdateQuestPropertyUseCase updateQuestPropertyUseCase;

	@GetMapping
	public ResponseEntity<ReadQuestPropertyResponse> find(@Auth AuthInfo authInfo) {
		QuestPropertyDto dto = readQuestPropertyUseCase.find(authInfo.getMemberId());
		return ResponseEntity.ok(ReadQuestPropertyResponse.of(dto));
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateQuestPropertyRequest request) {
		updateQuestPropertyUseCase.update(request.toCommand(authInfo.getMemberId()));
		return ResponseEntity.noContent().build();
	}
}
