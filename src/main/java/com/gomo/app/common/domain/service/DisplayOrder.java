package com.gomo.app.common.domain.service;

import com.gomo.app.common.domain.ValueObject;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class DisplayOrder {

	private int displayOrder;

	protected DisplayOrder() {
	}

	private DisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public static DisplayOrder of(int displayOrder) {
		return new DisplayOrder(displayOrder);
	}

	private boolean isNonNegative() {
		return displayOrder >= 0;
	}
}
