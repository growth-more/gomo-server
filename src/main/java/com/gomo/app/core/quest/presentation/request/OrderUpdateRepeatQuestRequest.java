package com.gomo.app.core.quest.presentation.request;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.displayorder.UpdatedOrderDto;
import com.gomo.app.core.quest.application.port.command.OrderUpdateRepeatQuestCommand;

import lombok.Getter;

@Getter
public class OrderUpdateRepeatQuestRequest {

	private String questType;
	private List<UpdatedOrderDto> updatedOrders;

	private OrderUpdateRepeatQuestRequest(String questType, List<UpdatedOrderDto> updatedOrders) {
		this.questType = questType;
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateRepeatQuestRequest of(String questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateRepeatQuestRequest(questType, updatedOrders);
	}

	public OrderUpdateRepeatQuestCommand toCommand(UUID participantId) {
		return OrderUpdateRepeatQuestCommand.of(participantId, questType, updatedOrders);
	}
}
