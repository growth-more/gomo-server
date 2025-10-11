package com.gomo.app.core.point.fixture;

import java.util.UUID;

import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;

public class PointWalletFixture {

	public static PointWallet create() {
		return PointWallet.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			Balance.of(1000)
		);
	}

	public static PointWallet create(UUID transactionId, int balance) {
		return PointWallet.of(
			UUID.randomUUID(),
			transactionId,
			Balance.of(balance)
		);
	}
}
