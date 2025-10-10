package com.gomo.app.core.point.presentation.api.response;

import lombok.Getter;

@Getter
public class ReadBalanceResponse {

	private final int amount;

	private ReadBalanceResponse(int amount) {
		this.amount = amount;
	}

	public static ReadBalanceResponse of(int balance) {
		return new ReadBalanceResponse(balance);
	}
}
