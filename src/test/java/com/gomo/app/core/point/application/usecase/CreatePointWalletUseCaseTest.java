package com.gomo.app.core.point.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.point.domain.model.PointWallet;
import com.gomo.app.core.point.domain.repository.PointWalletRepository;
import com.gomo.app.core.point.fixture.PointWalletFixture;

@DisplayName("[Application unit]: 포인트 지갑 생성 테스트")
@ExtendWith(MockitoExtension.class)
class CreatePointWalletUseCaseTest {

	@InjectMocks
	private CreatePointWalletUseCase sut;

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
}
