package com.gomo.app.core.point.domain.service;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.point.domain.model.SourceType;
import com.gomo.app.core.point.domain.model.TransactionType;
import com.gomo.app.core.point.domain.repository.PointRepository;
import com.gomo.app.core.point.fixture.PointFixture;

@DisplayName("[Domain unit]: 포인트 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class PointServiceTest {

	@InjectMocks
	private PointService sut;

	@Mock
	private PointWalletService pointWalletService;

	@Mock
	private PointRepository pointRepository;

	@DisplayName("포인트를 생성한다.")
	@Test
	void create_point() {
		doReturn(PointFixture.create()).when(pointRepository).save(any());
		sut.create(UUID.randomUUID(), SourceType.QUEST, TransactionType.GAIN, 10);
		verify(pointWalletService, times(1)).adjustPointBalance(any(), any(), eq(10));
		verify(pointRepository, times(1)).save(any());
	}
}
