package com.gomo.app.displayorder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record OrderUpdateOrderChangeableCommand(Map<UUID, OrderChangeable> originOrderChangeableMap, List<UpdatedOrderDto> updatedOrders) {

	public static OrderUpdateOrderChangeableCommand of(Map<UUID, OrderChangeable> originOrderChangeableMap, List<UpdatedOrderDto> updatedOrders) {
		return new OrderUpdateOrderChangeableCommand(originOrderChangeableMap, updatedOrders);
	}
}
