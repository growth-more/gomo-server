package com.gomo.app.core.streak.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.streak.application.port.dto.ListStreakDto;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.service.StreakService;
import com.gomo.app.core.streak.fixture.StreakFixture;

@DisplayName("[Application unit]: 스트릭 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadStreakUseCaseTest {

	@InjectMocks
	private ReadStreakUseCase sut;

	@Mock
	private StreakService streakService;

	@DisplayName("스트릭 목록을 조회한다.")
	@Test
	void find_All() {
		List<Streak> dailyStreaks = List.of(StreakFixture.create(StreakType.DAILY), StreakFixture.create(StreakType.DAILY));
		List<Streak> weeklyStreaks = List.of(StreakFixture.create(StreakType.WEEKLY));
		List<Streak> monthlyStreaks = List.of();

		doReturn(dailyStreaks).when(streakService).findAllByStreakType(any(), eq(StreakType.DAILY), any(), any());
		doReturn(weeklyStreaks).when(streakService).findAllByStreakType(any(), eq(StreakType.WEEKLY), any(), any());
		doReturn(monthlyStreaks).when(streakService).findAllByStreakType(any(), eq(StreakType.MONTHLY), any(), any());

		ListStreakDto actual = sut.findAll(UUID.randomUUID(), LocalDate.of(2025, 2, 6), LocalDate.of(2025, 2, 7));

		assertThat(actual.dailyStreaks().size()).isEqualTo(2);
		assertThat(actual.weeklyStreaks().size()).isEqualTo(1);
		assertThat(actual.monthlyStreaks().size()).isEqualTo(0);
	}
}
