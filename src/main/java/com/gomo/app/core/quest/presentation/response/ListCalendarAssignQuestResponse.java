package com.gomo.app.core.quest.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListCalendarAssignQuestResponse {

	private List<CalendarAssignQuestResponse> assignQuests;

	private ListCalendarAssignQuestResponse(List<CalendarAssignQuestResponse> assignQuests) {
		this.assignQuests = assignQuests;
	}

	public static ListCalendarAssignQuestResponse of(List<CalendarAssignQuestResponse> assignQuests) {
		return new ListCalendarAssignQuestResponse(assignQuests);
	}
}
