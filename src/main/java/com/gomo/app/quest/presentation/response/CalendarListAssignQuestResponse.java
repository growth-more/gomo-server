package com.gomo.app.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class CalendarListAssignQuestResponse {

	private List<CalendarReadAssignQuestResponse> assignQuests;

	private CalendarListAssignQuestResponse(List<CalendarReadAssignQuestResponse> assignQuests) {
		this.assignQuests = assignQuests;
	}

	public static CalendarListAssignQuestResponse of(List<CalendarReadAssignQuestResponse> assignQuests) {
		return new CalendarListAssignQuestResponse(assignQuests);
	}
}
