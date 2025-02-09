package com.gomo.app.point.presentation.response;

import com.gomo.app.point.domain.model.Balance;

import lombok.Getter;

@Getter
public class ReadBalanceResponse {

	private int amount;

	private ReadBalanceResponse(int amount) {
		this.amount = amount;
	}

	public static ReadBalanceResponse of(Balance balance) {
		return new ReadBalanceResponse(balance.getAmount());
	}
}
