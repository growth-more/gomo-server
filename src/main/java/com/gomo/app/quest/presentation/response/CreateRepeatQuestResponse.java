package com.gomo.app.quest.presentation.response;

import java.util.UUID;

import com.gomo.app.quest.domain.model.RepeatQuestId;

import lombok.Getter;

@Getter
public class CreateRepeatQuestResponse {

	private UUID id;

	private CreateRepeatQuestResponse(UUID id) {
		this.id = id;
	}

	public static CreateRepeatQuestResponse of(RepeatQuestId id) {
		return new CreateRepeatQuestResponse(id.getId());
	}
}
