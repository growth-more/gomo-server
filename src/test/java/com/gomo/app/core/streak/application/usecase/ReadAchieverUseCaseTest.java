package com.gomo.app.core.streak.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.streak.application.port.dto.AchieverDto;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.service.AchieverService;
import com.gomo.app.core.streak.fixture.AchieverFixture;

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
		Achiever achiever = AchieverFixture.achiever();
		doReturn(achiever).when(achieverService).find(any());
		AchieverDto actual = sut.find(UUID.randomUUID());
		assertThat(actual).extracting("id", "longestStreakDays", "currentStreakDays")
			.containsExactly(achiever.id(), achiever.getLongestStreakDays(), achiever.getCurrentStreakDays());
	}
}
