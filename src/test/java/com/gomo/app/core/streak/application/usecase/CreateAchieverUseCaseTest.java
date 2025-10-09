package com.gomo.app.core.streak.application.usecase;

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

import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.core.streak.fixture.AchieverFixture;

@DisplayName("[Application unit]: 성취자 생성 테스트")
@ExtendWith(MockitoExtension.class)
class CreateAchieverUseCaseTest {

	@InjectMocks
	private CreateAchieverUseCase sut;

	@Mock
	private AchieverRepository achieverRepository;

	@DisplayName("성취자를 생성한다.")
	@Test
	void create_achiever() {
		doReturn(AchieverFixture.create()).when(achieverRepository).save(any());
		UUID actual = sut.create(UUID.randomUUID());
		assertThat(actual).isNotNull();
	}
}
