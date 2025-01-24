package com.gomo.app.quest.presentation.request;

import java.util.List;

import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class OrderUpdateAssignQuestRequest {

	private QuestType questType;
	private List<Integer> updatedOrders;

	private OrderUpdateAssignQuestRequest(
		QuestType questType,
		List<Integer> updatedOrders
	) {
		this.questType = questType;
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateAssignQuestRequest of(
		QuestType questType,
		List<Integer> updatedOrders
	) {
		return new OrderUpdateAssignQuestRequest(questType, updatedOrders);
	}
}
