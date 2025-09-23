package com.gomo.app.quest.presentation.request;

import java.util.List;
import java.util.UUID;

import com.gomo.app.displayorder.UpdatedOrderDto;
import com.gomo.app.quest.application.port.command.OrderUpdateRepeatQuestCommand;
import com.gomo.app.quest.domain.model.QuestType;

import lombok.Getter;

@Getter
public class OrderUpdateRepeatQuestRequest {

	private QuestType questType;
	private List<UpdatedOrderDto> updatedOrders;

	private OrderUpdateRepeatQuestRequest(QuestType questType, List<UpdatedOrderDto> updatedOrders) {
		this.questType = questType;
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateRepeatQuestRequest of(QuestType questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateRepeatQuestRequest(questType, updatedOrders);
	}

	public OrderUpdateRepeatQuestCommand toCommand(UUID participantId) {
		return OrderUpdateRepeatQuestCommand.of(participantId, questType, updatedOrders);
	}
}
