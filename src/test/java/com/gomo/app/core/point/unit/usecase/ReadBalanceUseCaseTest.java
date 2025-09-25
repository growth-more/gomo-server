package com.gomo.app.core.point.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.point.application.ReadBalanceUseCase;
import com.gomo.app.core.point.domain.model.Balance;
import com.gomo.app.core.point.domain.service.PointWalletService;
import com.gomo.app.core.point.presentation.response.ReadBalanceResponse;

@DisplayName("[Application unit]: 포인트 잔고 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadBalanceUseCaseTest {

	@InjectMocks
	private ReadBalanceUseCase sut;

	@Mock
	private PointWalletService pointWalletService;

	@DisplayName("사용자의 포인트 잔고를 조회한다.")
	@Test
	void find_balance_by_transactor_id() {
		doReturn(Balance.of(1000)).when(pointWalletService).findBalance(any());

		ReadBalanceResponse actual = sut.find(UUID.randomUUID());

		assertThat(actual.getAmount()).isEqualTo(1000);
	}
}
