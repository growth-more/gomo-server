package com.gomo.app.common.domain.service;

import static com.gomo.app.common.exception.DomainErrorCode.*;

import com.gomo.app.common.domain.ValueObject;
import com.gomo.app.common.exception.PolicyViolationException;

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
		if(isNegative(displayOrder)) {
			throw new PolicyViolationException(INVALID_PARAMETER, "DisplayOrder must be positive");
		}
		this.displayOrder = displayOrder;
	}

	public static DisplayOrder of(int displayOrder) {
		return new DisplayOrder(displayOrder);
	}

	public DisplayOrder increase(int increment) {
		if(isNegative(increment)) {
			throw new PolicyViolationException(INVALID_PARAMETER, "Increment must be positive");
		}
		return new DisplayOrder(this.displayOrder + increment);
	}

	private boolean isNegative(int displayOrder) {
		return displayOrder < 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
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
