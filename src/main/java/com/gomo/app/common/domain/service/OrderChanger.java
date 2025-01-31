package com.gomo.app.common.domain.service;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderChanger {

	public static void change(List<OrderChangeable> candidates, List<DisplayOrder> changedOrders) {
		for(int i=0; i<candidates.size(); i++) {
			candidates.get(i).changeOrder(changedOrders.get(i));
		}
		// IntStream.range(0, candidates.size())
		// 	.forEach(i -> candidates.get(i).changeOrder(changedOrders.get(i)));
	}
}
