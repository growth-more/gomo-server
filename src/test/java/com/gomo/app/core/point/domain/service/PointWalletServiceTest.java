package com.gomo.app.core.point.domain.service;

import static org.assertj.core.api.Assertions.*;
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

@DisplayName("[Domain unit]: 포인트 잔고 조회 및 조정 테스트")
@ExtendWith(MockitoExtension.class)
public class PointWalletServiceTest {

	@InjectMocks
	PointWalletService sut;

	@Mock
	private PointWalletRepository pointWalletRepository;

	@DisplayName("사용자의 포인트 잔고를 조회한다.")
	@Test
	void find_balance() {
		PointWallet pointWallet = PointWalletFixture.point(UUID.randomUUID(), 1660);
		doReturn(Optional.of((pointWallet))).when(pointWalletRepository).findByTransactorId(any());

		Balance balance = sut.findBalance(pointWallet.getTransactorId());

		assertThat(balance.getAmount()).isEqualTo(pointWallet.getBalance().getAmount());
	}

	@DisplayName("사용자 포인트 잔고가 증가한다.")
	@Test
	void enhance_balance() {
		PointWallet pointWallet = PointWalletFixture.point(UUID.randomUUID(), 1660);
		doReturn(Optional.of((pointWallet))).when(pointWalletRepository).findByTransactorId(any());

		sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, 40);

		PointWallet updatedWallet = pointWalletRepository.findByTransactorId(pointWallet.getTransactorId()).get();
		assertThat(updatedWallet.getBalance().getAmount()).isEqualTo(1700);
	}

	@DisplayName("사용자 포인트 잔고가 감소한다.")
	@Test
	void reduce_balance() {
		PointWallet pointWallet = PointWalletFixture.point(UUID.randomUUID(), 1660);
		doReturn(Optional.of((pointWallet))).when(pointWalletRepository).findByTransactorId(any());

		sut.adjustPointBalance(pointWallet.getTransactorId(), TransactionType.GAIN, -60);

		PointWallet updatedWallet = pointWalletRepository.findByTransactorId(pointWallet.getTransactorId()).get();
		assertThat(updatedWallet.getBalance().getAmount()).isEqualTo(1600);
	}
}
