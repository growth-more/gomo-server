package com.gomo.app.point.unit.domain;

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
import com.gomo.app.core.point.domain.model.TransactorId;
import com.gomo.app.core.point.domain.repository.PointRepository;
import com.gomo.app.core.point.domain.service.PointService;
import com.gomo.app.core.point.domain.service.PointWalletService;
import com.gomo.app.point.fixture.PointFixture;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain integration]: 포인트 생성 테스트")
public class PointServiceTest {

	@InjectMocks
	PointService sut;

	@Mock
	PointWalletService pointWalletService;

	@Mock
	PointRepository pointRepository;

	@DisplayName("포인트를 생성한다.")
	@Test
	void create_point() {
		doReturn(PointFixture.point()).when(pointRepository).save(any());
		sut.create(TransactorId.of(UUID.randomUUID()), SourceType.QUEST, TransactionType.GAIN, 10);
		verify(pointWalletService, times(1)).adjustPointBalance(any(), any(), eq(10));
		verify(pointRepository, times(1)).save(any());
	}
	
}
