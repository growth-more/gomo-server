package com.gomo.app.core.streak.application.usecase;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.service.StreakService;

@DisplayName("[Application unit]: 스트릭 생성 테스트")
@ExtendWith(MockitoExtension.class)
class CreateStreakUseCaseTest {

	@InjectMocks
	private CreateStreakUseCase sut;

	@Mock
	private StreakService streakService;

	@DisplayName("스트릭을 생성한다.")
	@Test
	void create_streak() {
		sut.create(UUID.randomUUID(), QuestType.DAILY.name(), LocalDate.now());
		verify(streakService, times(1)).fill(any(Streak.class));
	}
}
