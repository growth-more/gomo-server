package com.gomo.app.core.point.fixture;

import java.util.UUID;

import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;

public class PointWalletFixture {

	public static PointWallet create() {
		return PointWallet.of(
			PointWalletId.of(UUID.randomUUID()),
			TransactorId.of(UUID.randomUUID()),
			Balance.of(1000)
		);
	}

	public static PointWallet create(UUID transactionId, int balance) {
		return PointWallet.of(
			PointWalletId.of(UUID.randomUUID()),
			TransactorId.of(transactionId),
			Balance.of(balance)
		);
	}
}
