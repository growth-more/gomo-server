package com.gomo.app.point.fixture;

import java.util.UUID;

import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.PointWalletId;
import com.gomo.app.point.domain.model.TransactorId;

public class PointWalletFixture {

	public static PointWallet point(UUID transactionId, int balance) {
		return PointWallet.of(
			PointWalletId.of(UUID.randomUUID()),
			TransactorId.of(transactionId),
			Balance.of(balance)
		);
	}
}
