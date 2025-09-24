package com.gomo.app.core.interest.presentation.request;

import java.util.List;
import java.util.UUID;

import com.gomo.app.common.displayorder.UpdatedOrderDto;
import com.gomo.app.core.interest.application.port.command.OrderUpdateMajorInterestCommand;

import lombok.Getter;

@Getter
public class OrderUpdateMajorInterestRequest {

	private List<UpdatedOrderDto> updatedOrders;

	private OrderUpdateMajorInterestRequest(List<UpdatedOrderDto> updatedOrders) {
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateMajorInterestRequest of(List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateMajorInterestRequest(updatedOrders);
	}

	public OrderUpdateMajorInterestCommand toCommand(UUID registrantId) {
		return OrderUpdateMajorInterestCommand.of(registrantId, updatedOrders);
	}
}
