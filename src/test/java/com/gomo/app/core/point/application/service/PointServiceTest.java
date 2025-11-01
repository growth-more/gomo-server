package com.gomo.app.core.point.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.web.PageRequest;
import com.gomo.app.core.point.application.port.dto.ListPointDto;
import com.gomo.app.core.point.domain.repository.PointRepository;
import com.gomo.app.core.point.fixture.PointFixture;

@DisplayName("[Application unit]: 포인트 생성 테스트")
@ExtendWith(MockitoExtension.class)
class PointServiceTest {

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
		sut.create(UUID.randomUUID(), "QUEST", "GAIN", 10);
		verify(pointWalletService, times(1)).adjustPointBalance(any(), any(), eq(10));
		verify(pointRepository, times(1)).save(any());
	}

	@DisplayName("포인트 목록을 조회한다.")
	@Test
	void read_points() {
		doReturn(List.of(PointFixture.create(), PointFixture.create())).when(pointRepository).findAllByTransactorId(any(), any(), any(), eq(10));

		ListPointDto actual = sut.readAll(UUID.randomUUID(), PageRequest.of(10, null, null));

		assertThat(actual.points().size()).isEqualTo(2);
		assertThat(actual.lastElementId()).isNotNull();
	}

	@DisplayName("포인트 목록이 없다면, 마지막 원소는 null이 된다.")
	@Test
	void read_empty_points() {
		doReturn(List.of()).when(pointRepository).findAllByTransactorId(any(), any(), any(), eq(10));

		ListPointDto actual = sut.readAll(UUID.randomUUID(), PageRequest.of(10, null, null));

		assertThat(actual.lastElementId()).isNull();
	}
}
