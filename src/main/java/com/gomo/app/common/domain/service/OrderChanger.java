package com.gomo.app.common.domain.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.gomo.app.interest.presentation.request.UpdateOrderRequest;

@Component
public class OrderChanger {

	public static void change(Map<UUID, OrderChangeable> orderChangeableMap, List<UpdateOrderRequest> updateOrderRequests) {
		List<OrderChangeable> candidates = updateOrderRequests.stream()
			.map(updateOrderRequest -> orderChangeableMap.get(updateOrderRequest.getId()))
			.toList();

		List<DisplayOrder> changedOrders = updateOrderRequests.stream()
			.map(updateOrderRequest -> DisplayOrder.of(updateOrderRequest.getDisplayOrder()))
			.toList();

		IntStream.range(0, candidates.size()).forEach(i -> candidates.get(i).changeOrder(changedOrders.get(i)));
	}
}
