package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.ReadQuestPropertyUseCase;
import com.gomo.app.member.application.UpdateQuestPropertyUseCase;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;
import com.gomo.app.member.presentation.response.ReadQuestPropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/members/properties/quests")
@Presentation
public class QuestPropertyApi {

	private final ReadQuestPropertyUseCase readQuestPropertyUseCase;
	private final UpdateQuestPropertyUseCase updateQuestPropertyUseCase;

	@GetMapping
	public ResponseEntity<ReadQuestPropertyResponse> find(@Auth AuthInfo authInfo) {
		ReadQuestPropertyResponse response = readQuestPropertyUseCase.find(MemberId.of(authInfo.getMemberId()));
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateQuestPropertyRequest request) {
		updateQuestPropertyUseCase.update(MemberId.of(authInfo.getMemberId()), request);
		return ResponseEntity.noContent().build();
	}
}
