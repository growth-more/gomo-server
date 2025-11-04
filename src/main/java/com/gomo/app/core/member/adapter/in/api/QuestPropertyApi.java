package com.gomo.app.core.member.adapter.in.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.member.adapter.in.api.request.UpdateQuestPropertyRequest;
import com.gomo.app.core.member.adapter.in.api.response.ReadQuestPropertyResponse;
import com.gomo.app.core.member.application.port.dto.QuestPropertyDto;
import com.gomo.app.core.member.application.port.in.QuestPropertyReader;
import com.gomo.app.core.member.application.port.in.QuestPropertyUpdater;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members/properties/quests")
@CoreApi
public class QuestPropertyApi {

	private final QuestPropertyReader questPropertyReader;
	private final QuestPropertyUpdater questPropertyUpdater;

	@GetMapping
	public ResponseEntity<ReadQuestPropertyResponse> find(@Session SessionInfo sessionInfo) {
		QuestPropertyDto dto = questPropertyReader.read(sessionInfo.getPrincipalId());
		return ResponseEntity.ok(ReadQuestPropertyResponse.of(dto));
	}

	@PutMapping
	public ResponseEntity<Void> update(@Session SessionInfo sessionInfo, @RequestBody UpdateQuestPropertyRequest request) {
		questPropertyUpdater.update(request.toCommand(sessionInfo.getPrincipalId()));
		return ResponseEntity.noContent().build();
	}
}
