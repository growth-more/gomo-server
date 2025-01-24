package com.gomo.app.interest.presentation.request;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderUpdateMajorInterestRequest {

	private List<Integer> updatedOrders;

	private OrderUpdateMajorInterestRequest(List<Integer> updatedOrders) {
		this.updatedOrders = updatedOrders;
	}

	public static OrderUpdateMajorInterestRequest of(List<Integer> updatedOrders) {
		return new OrderUpdateMajorInterestRequest(updatedOrders);
	}
}
