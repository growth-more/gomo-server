package com.gomo.app.quest.presentation.response;

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
