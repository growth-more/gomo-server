package com.gomo.app.displayorder;

import java.util.UUID;

public record UpdatedOrderDto(UUID orderChangeableId, int displayOrder) {

	public static UpdatedOrderDto of(UUID orderChangeableId, int displayOrder) {
		return new UpdatedOrderDto(orderChangeableId, displayOrder);
	}
}
