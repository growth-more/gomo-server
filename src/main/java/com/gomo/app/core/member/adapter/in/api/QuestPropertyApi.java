package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.core.member.adapter.in.api.request.UpdateQuestPropertyRequest;
import com.gomo.app.core.member.adapter.in.api.response.ReadQuestPropertyResponse;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.application.port.in.QuestPropertyReader;
import com.gomo.app.core.member.application.port.in.QuestPropertyUpdater;
import com.gomo.app.core.auth.adapter.in.security.Auth;
import com.gomo.app.core.auth.adapter.in.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/properties/quests")
@CoreApi
public class QuestPropertyApi {

	private final QuestPropertyReader questPropertyReader;
	private final QuestPropertyUpdater questPropertyUpdater;

	@GetMapping
	public ResponseEntity<ReadQuestPropertyResponse> find(@Auth AuthInfo authInfo) {
		QuestPropertyDto dto = questPropertyReader.read(authInfo.getPrincipalId());
		return ResponseEntity.ok(ReadQuestPropertyResponse.of(dto));
	}

	@PutMapping
	public ResponseEntity<Void> update(@Auth AuthInfo authInfo, @RequestBody UpdateQuestPropertyRequest request) {
		questPropertyUpdater.update(request.toCommand(authInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
