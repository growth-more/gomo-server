package com.gomo.app.interest.application.port.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.displayorder.UpdatedOrderDto;

public record OrderUpdateMajorInterestCommand(UUID registrantId, List<UpdatedOrderDto> updatedOrders) {

	public static OrderUpdateMajorInterestCommand of(UUID registrantId, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateMajorInterestCommand(registrantId, updatedOrders);
	}
}
