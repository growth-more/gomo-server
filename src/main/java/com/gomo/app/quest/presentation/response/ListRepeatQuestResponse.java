package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListRepeatQuestResponse {

	private List<ReadRepeatQuestResponse> repeatQuests;

	private ListRepeatQuestResponse(List<ReadRepeatQuestResponse> repeatQuests) {
		this.repeatQuests = repeatQuests;
	}

	public static ListRepeatQuestResponse of(List<ReadRepeatQuestResponse> repeatQuests) {
		return new ListRepeatQuestResponse(repeatQuests);
	}
}
