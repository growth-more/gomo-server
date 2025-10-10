package com.gomo.app.core.quest.application.port.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.displayorder.UpdatedOrderDto;

public record OrderUpdateAssignQuestCommand(UUID participantId, String questType, List<UpdatedOrderDto> updatedOrders) {

	public static OrderUpdateAssignQuestCommand of(UUID participantId, String questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateAssignQuestCommand(participantId, questType, updatedOrders);
	}
}
