package com.gomo.app.point.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;

@DisplayName("[Domain unit]: 포인트 지갑 생성 및 수정 테스트")
public class PointWalletTest {

	private static final PointWalletId POINT_WALLET_ID = PointWalletId.of(UUID.randomUUID());
	private static final TransactorId TRANSACTOR_ID = TransactorId.of(UUID.randomUUID());

	@DisplayName("포인트 지갑을 생성한다.")
	@Test
	void create_point_wallet() {
		PointWallet pointWallet = PointWallet.of(POINT_WALLET_ID, TRANSACTOR_ID, Balance.of(1000));

		assertThat(pointWallet)
			.extracting("id", "transactorId", "balance")
			.containsExactly(POINT_WALLET_ID, TRANSACTOR_ID, Balance.of(1000));
	}

	@DisplayName("포인트 지갑의 잔고 기본값은 0이다.")
	@Test
	void create_default_point_wallet() {
		PointWallet pointWallet = PointWallet.createDefault(POINT_WALLET_ID, TRANSACTOR_ID);

		assertThat(pointWallet)
			.extracting("id", "transactorId", "balance")
			.containsExactly(POINT_WALLET_ID, TRANSACTOR_ID, Balance.of(0));
	}

	@DisplayName("잔고를 조정한다.")
	@Test
	void update_balance() {
		PointWallet pointWallet = PointWallet.of(POINT_WALLET_ID, TRANSACTOR_ID, Balance.of(1000));
		pointWallet.adjustBalance(500);

		assertThat(pointWallet.getBalance()).isEqualTo(Balance.of(1500));
	}
}
