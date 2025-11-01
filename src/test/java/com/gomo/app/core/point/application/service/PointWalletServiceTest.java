package com.gomo.app.core.point.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.point.fixture.PointWalletFixture;

@DisplayName("[Application unit]: 포인트 지갑 생성 테스트")
@ExtendWith(MockitoExtension.class)
class PointWalletServiceTest {

	@InjectMocks
	private PointWalletService sut;

	@Mock
	private PointWalletRepository pointWalletRepository;

	@DisplayName("포인트 지갑을 생성한다.")
	@Test
	void create_point_wallet() {
		PointWallet pointWallet = PointWalletFixture.create();
		doReturn(pointWallet).when(pointWalletRepository).save(any());

		UUID actual = sut.create(UUID.randomUUID());

		assertThat(actual).isEqualTo(pointWallet.getId());
	}

	@DisplayName("사용자의 포인트 잔고를 조회한다.")
	@Test
	void find_balance() {
		PointWallet pointWallet = PointWalletFixture.create(UUID.randomUUID(), 1660);
		doReturn(Optional.of((pointWallet))).when(pointWalletRepository).findByTransactorId(any());

		Balance balance = sut.findBalance(pointWallet.getTransactorId());

		assertThat(balance.getAmount()).isEqualTo(pointWallet.getBalance().getAmount());
	}

	@DisplayName("사용자 포인트 잔고가 증가한다.")
	@Test
	void enhance_balance() {
		PointWallet pointWallet = PointWalletFixture.create(UUID.randomUUID(), 1660);
		doReturn(Optional.of((pointWallet))).when(pointWalletRepository).findByTransactorId(any());

		sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, 40);

		PointWallet updatedWallet = pointWalletRepository.findByTransactorId(pointWallet.getTransactorId()).get();
		assertThat(updatedWallet.getBalance().getAmount()).isEqualTo(1700);
	}

	@DisplayName("사용자 포인트 잔고가 감소한다.")
	@Test
	void reduce_balance() {
		PointWallet pointWallet = PointWalletFixture.create(UUID.randomUUID(), 1660);
		doReturn(Optional.of((pointWallet))).when(pointWalletRepository).findByTransactorId(any());

		sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, -60);

		PointWallet updatedWallet = pointWalletRepository.findByTransactorId(pointWallet.getTransactorId()).get();
		assertThat(updatedWallet.getBalance().getAmount()).isEqualTo(1600);
	}
}
