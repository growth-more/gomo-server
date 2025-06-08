package com.gomo.app.point.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.dto.PageRequest;
import com.gomo.app.point.application.ReadPointUseCase;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.repository.PointRepository;
import com.gomo.app.point.fixture.PointFixture;
import com.gomo.app.point.presentation.response.ListPointResponse;

@DisplayName("[Application unit]: 포인트 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadPointUseCaseTest {

	@InjectMocks
	private ReadPointUseCase sut;

	@Mock
	private PointRepository pointRepository;

	@DisplayName("포인트 목록을 조회한다.")
	@Test
	void find_points() {
		doReturn(List.of(PointFixture.point(), PointFixture.point())).when(pointRepository).findAllByTransactorId(any(), any(), eq(10));

		ListPointResponse actual = sut.findAll(TransactorId.of(UUID.randomUUID()), PageRequest.of(10, null));

		assertThat(actual.getPoints().size()).isEqualTo(2);
		assertThat(actual.getLastElementId()).isNotNull();
	}

	@DisplayName("포인트 목록이 없다면, 마지막 원소는 null이 된다.")
	@Test
	void find_empty_points() {
		doReturn(List.of()).when(pointRepository).findAllByTransactorId(any(), any(), eq(10));

		ListPointResponse actual = sut.findAll(TransactorId.of(UUID.randomUUID()), PageRequest.of(10, null));

		assertThat(actual.getLastElementId()).isNull();
	}
}
