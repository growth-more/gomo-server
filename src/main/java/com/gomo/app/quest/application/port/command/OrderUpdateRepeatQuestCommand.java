package com.gomo.app.quest.application.port.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.displayorder.UpdatedOrderDto;

public record OrderUpdateRepeatQuestCommand(UUID participantId, String questType, List<UpdatedOrderDto> updatedOrders) {

	public static OrderUpdateRepeatQuestCommand of(UUID participantId, String questType, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateRepeatQuestCommand(participantId, questType, updatedOrders);
	}
}
