package com.gomo.app.core.streak.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.streak.application.ReadAchieverUseCase;
import com.gomo.app.core.streak.domain.service.AchieverService;
import com.gomo.app.core.streak.fixture.AchieverFixture;
import com.gomo.app.core.streak.presentation.response.ReadAchieverResponse;

@DisplayName("[Application unit]: 성취자 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadAchieverUseCaseTest {

	@InjectMocks
	private ReadAchieverUseCase sut;

	@Mock
	private AchieverService achieverService;

	@DisplayName("성취자를 조회한다.")
	@Test
	void create_achiever() {
		doReturn(AchieverFixture.achiever()).when(achieverService).find(any());
		ReadAchieverResponse actual = sut.find(UUID.randomUUID());
		assertThat(actual).isNotNull();
	}
}
