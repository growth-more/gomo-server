package com.gomo.app.interest.presentation.request;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderUpdateMajorInterestRequest {

	private List<UpdateOrderRequest> updateOrderRequests;

	private OrderUpdateMajorInterestRequest(List<UpdateOrderRequest> updateOrderRequests) {
		this.updateOrderRequests = updateOrderRequests;
	}

	public static OrderUpdateMajorInterestRequest of(List<UpdateOrderRequest> updatedOrders) {
		return new OrderUpdateMajorInterestRequest(updatedOrders);
	}
}
