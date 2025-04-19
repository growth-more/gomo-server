package com.gomo.app.interest.presentation.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UpdateOrderRequest {

	private UUID id;
	private int displayOrder;

	private UpdateOrderRequest(
		UUID id,
		int displayOrder
	) {
		this.id = id;
		this.displayOrder = displayOrder;
	}

	public static UpdateOrderRequest of(UUID candidateId,
		int displayOrder
	) {
		return new UpdateOrderRequest(candidateId, displayOrder);
	}
}
