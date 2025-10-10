package com.gomo.app.common.displayorder;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class OrderChanger {

	public static void change(OrderUpdateOrderChangeableCommand command) {
		Map<UUID, OrderChangeable> originOrderChangeableMap = command.originOrderChangeableMap();
		List<UpdatedOrderDto> updatedOrders = command.updatedOrders();
		List<OrderChangeable> candidates = updatedOrders.stream()
			.map(updatedOrder -> originOrderChangeableMap.get(updatedOrder.orderChangeableId()))
			.toList();

		List<DisplayOrder> changedOrders = updatedOrders.stream()
			.map(updatedOrder -> DisplayOrder.of(updatedOrder.displayOrder()))
			.toList();

		IntStream.range(0, candidates.size()).forEach(i -> candidates.get(i).changeOrder(changedOrders.get(i)));
	}
}
