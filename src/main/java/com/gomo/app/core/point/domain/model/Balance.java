package com.gomo.app.core.point.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.point.domain.exception.InsufficientBalanceException;
import com.gomo.app.core.point.domain.exception.code.BalanceErrorCode;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Balance {

	private int amount;

	protected Balance() {
	}

	private Balance(int amount) {
		ensureAmountNotNegative(amount);
		this.amount = amount;
	}

	public static Balance of(int amount) {
		return new Balance(amount);
	}

	public Balance update(int deltaAmount) {
		ensureAmountNotNegative(this.amount + deltaAmount);
		return new Balance(amount + deltaAmount);
	}

	private void ensureAmountNotNegative(int amount) {
		if (amount < 0) {
			throw new InsufficientBalanceException(BalanceErrorCode.INSUFFICIENT_BALANCE);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Balance balance = (Balance)o;
		return amount == balance.amount;
	}

	@Override
	public int hashCode() {
		return amount;
	}
}
