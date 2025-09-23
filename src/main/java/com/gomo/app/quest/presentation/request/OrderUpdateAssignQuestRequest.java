package com.gomo.app.quest.presentation.request;

import java.util.List;
import java.util.UUID;

import com.gomo.app.displayorder.UpdatedOrderDto;
import com.gomo.app.quest.application.port.command.OrderUpdateAssignQuestCommand;
import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class OrderUpdateAssignQuestRequest {

	private QuestType questType;
	private List<UpdatedOrderDto> updatedOrders;

	private OrderUpdateAssignQuestRequest(QuestType questType, List<UpdatedOrderDto> updatedOrders) {
		this.questType = questType;
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateAssignQuestRequest of(QuestType questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateAssignQuestRequest(questType, updatedOrders);
	}

	public OrderUpdateAssignQuestCommand toCommand(UUID participantId) {
		return OrderUpdateAssignQuestCommand.of(participantId, questType, updatedOrders);
	}
}
