package com.gomo.app.quest.presentation.request;

import java.util.List;
import java.util.UUID;

import com.gomo.app.displayorder.UpdatedOrderDto;
import com.gomo.app.quest.application.port.command.OrderUpdateAssignQuestCommand;

import lombok.Getter;

@Getter
public class OrderUpdateAssignQuestRequest {

	private String questType;
	private List<UpdatedOrderDto> updatedOrders;

	private OrderUpdateAssignQuestRequest(String questType, List<UpdatedOrderDto> updatedOrders) {
		this.questType = questType;
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateAssignQuestRequest of(String questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateAssignQuestRequest(questType, updatedOrders);
	}

	public OrderUpdateAssignQuestCommand toCommand(UUID participantId) {
		return OrderUpdateAssignQuestCommand.of(participantId, questType, updatedOrders);
	}
}
