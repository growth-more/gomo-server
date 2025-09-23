package com.gomo.app.quest.application.port.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.displayorder.UpdatedOrderDto;
import com.gomo.app.quest.domain.model.QuestType;

public record OrderUpdateAssignQuestCommand(UUID participantId, QuestType questType, List<UpdatedOrderDto> updatedOrders) {

	public static OrderUpdateAssignQuestCommand of(UUID participantId, QuestType questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateAssignQuestCommand(participantId, questType, updatedOrders);
	}
}
