package com.gomo.app.core.quest.adapter.in.api.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateRepeatQuestResponse {

	private UUID id;

	private CreateRepeatQuestResponse(UUID id) {
		this.id = id;
	}

	public static CreateRepeatQuestResponse of(UUID id) {
		return new CreateRepeatQuestResponse(id);
	}
}
