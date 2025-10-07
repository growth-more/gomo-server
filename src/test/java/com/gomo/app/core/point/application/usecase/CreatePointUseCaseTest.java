package com.gomo.app.core.point.application.usecase;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyInt;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.point.domain.service.PointService;

@DisplayName("[Application unit]: 포인트 생성 테스트")
@ExtendWith(MockitoExtension.class)
class CreatePointUseCaseTest {

	@InjectMocks
	private CreatePointUseCase sut;

	@Mock
	private PointService pointService;

	@DisplayName("포인트를 생성한다.")
	@Test
	void create_point() {
		sut.create(UUID.randomUUID(), "QUEST", "GAIN", 10);
		verify(pointService, times(1)).create(any(), any(), any(), anyInt());
	}
}
