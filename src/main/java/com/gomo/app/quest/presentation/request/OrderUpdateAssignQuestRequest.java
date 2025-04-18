package com.gomo.app.quest.presentation.request;

import java.util.List;

import com.gomo.app.interest.presentation.request.UpdateOrderRequest;
import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class OrderUpdateAssignQuestRequest {

	private QuestType questType;
	private List<UpdateOrderRequest> updateOrderRequests;

	private OrderUpdateAssignQuestRequest(
		QuestType questType,
		List<UpdateOrderRequest> updateOrderRequests
	) {
		this.questType = questType;
		this.updateOrderRequests = updateOrderRequests;
	}

	public static OrderUpdateAssignQuestRequest of(
		QuestType questType,
		List<UpdateOrderRequest> updatedOrders
	) {
		return new OrderUpdateAssignQuestRequest(questType, updatedOrders);
	}
}
