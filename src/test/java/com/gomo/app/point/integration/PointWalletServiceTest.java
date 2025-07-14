package com.gomo.app.point.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.point.domain.model.Balance;
import com.gomo.app.point.domain.model.PointWallet;
import com.gomo.app.point.domain.model.TransactionType;
import com.gomo.app.point.domain.repository.PointWalletRepository;
import com.gomo.app.point.domain.service.PointWalletService;
import com.gomo.app.point.fixture.PointWalletFixture;

@DisplayName("[Domain integration]: 포인트 잔고 조회 및 조정 테스트")
public class PointWalletServiceTest extends IntegrationTestBase {

	@Autowired
	PointWalletService sut;

	@Autowired
	private PointWalletRepository pointWalletRepository;
	private PointWallet pointWallet;

	@BeforeEach
	public void setUp() {
		pointWallet = pointWalletRepository.save(PointWalletFixture.point(UUID.randomUUID(), 1660));
	}

	@AfterEach
	void tearDown() {
		pointWalletRepository.deleteAllInBatch();
	}

	@DisplayName("사용자의 포인트 잔고를 조회한다.")
	@Test
	void find_balance() {
		Balance balance = sut.findBalance(pointWallet.getTransactorId());

		assertThat(balance.getAmount()).isEqualTo(pointWallet.getBalance().getAmount());
	}

	@DisplayName("사용자 포인트 잔고가 증가한다.")
	@Test
	void enhance_balance() {
		sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, 40);

		PointWallet updatedWallet = pointWalletRepository.findByTransactorId(pointWallet.getTransactorId()).get();
		assertThat(updatedWallet.getBalance().getAmount()).isEqualTo(1700);
	}

	@DisplayName("사용자 포인트 잔고가 감소한다.")
	@Test
	void reduce_balance() {
		sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, -60);

		PointWallet updatedWallet = pointWalletRepository.findByTransactorId(pointWallet.getTransactorId()).get();
		assertThat(updatedWallet.getBalance().getAmount()).isEqualTo(1600);
	}
}
