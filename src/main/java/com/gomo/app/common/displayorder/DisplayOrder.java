package com.gomo.app.common.displayorder;

import com.gomo.app.common.ValueObject;

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
		if (isNegative(displayOrder)) {
			throw new DisplayOrderConstraintViolationException(DisplayOrderErrorCode.NON_POSITIVE);
		}
		this.displayOrder = displayOrder;
	}

	public static DisplayOrder of(int displayOrder) {
		return new DisplayOrder(displayOrder);
	}

	public DisplayOrder increase(int increment) {
		if (isNegative(increment)) {
			throw new DisplayOrderConstraintViolationException(DisplayOrderErrorCode.NON_POSITIVE_INCREMENT);
		}
		return new DisplayOrder(this.displayOrder + increment);
	}

	private boolean isNegative(int displayOrder) {
		return displayOrder < 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DisplayOrder order = (DisplayOrder)o;
		return this.displayOrder == order.displayOrder;
	}

	@Override
	public int hashCode() {
		return this.displayOrder;
	}
}
