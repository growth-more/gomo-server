package com.gomo.app.point.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.PointWalletId;
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.exception.InsufficientBalanceException;

@DisplayName("[Domain unit]: 잔고 생성 및 조정 테스트")
public class BalanceTest {

	private static final PointWalletId POINT_WALLET_ID = PointWalletId.of(UUID.randomUUID());
	private static final TransactorId TRANSACTOR_ID = TransactorId.of(UUID.randomUUID());

	@DisplayName("잔고를 생성한다.")
	@Test
	void create_balance() {
		Balance balance = Balance.of(1000);

		assertThat(balance.getAmount()).isEqualTo(1000);
	}

	@DisplayName("잔고는 음수가 될 수 없다.")
	@Test
	void create_negative_balance() {
		assertThatThrownBy(() -> Balance.of(-1000))
			.isInstanceOf(InsufficientBalanceException.class)
			.hasMessageContaining("Adjust fail due to insufficient balance");
	}

	@DisplayName("잔고를 조정한다.")
	@Test
	void update_balance() {
		PointWallet pointWallet = PointWallet.of(POINT_WALLET_ID, TRANSACTOR_ID, Balance.of(1000));
		pointWallet.adjustBalance(500);

		assertThat(pointWallet.getBalance()).isEqualTo(Balance.of(1500));
	}

	@DisplayName("현재 잔고보다 큰 값으로 감소시킬 수 없다.")
	@Test
	void update_negative_balance() {
		PointWallet pointWallet = PointWallet.of(POINT_WALLET_ID, TRANSACTOR_ID, Balance.of(1000));

		assertThatThrownBy(() -> pointWallet.adjustBalance(-1500))
			.isInstanceOf(InsufficientBalanceException.class)
			.hasMessageContaining("Adjust fail due to insufficient balance");
	}

	@DisplayName("같은 양의 잔고는 동일하게 취급한다.")
	@Test
	void same_balance() {
		Balance balance = Balance.of(1000);

		assertThat(balance).isEqualTo(balance);
		assertThat(balance).isEqualTo(Balance.of(1000));
	}

	@DisplayName("다른 양의 잔고는 다르게 취급한다.")
	@Test
	void not_same_balance() {
		Balance balance = Balance.of(1000);

		assertThat(balance).isNotEqualTo(Balance.of(2000));
	}
}
