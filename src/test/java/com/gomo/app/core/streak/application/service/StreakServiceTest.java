package com.gomo.app.core.streak.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.streak.application.port.dto.ListStreakDto;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.repository.StreakRepository;
import com.gomo.app.core.streak.fixture.AchieverFixture;
import com.gomo.app.core.streak.fixture.StreakFixture;

@DisplayName("[Application unit]: 스트릭 생성 테스트")
@ExtendWith(MockitoExtension.class)
class StreakServiceTest {

	@InjectMocks
	private StreakService sut;

	@Mock
	private AchieverService achieverService;

	@Mock
	private StreakRepository streakRepository;

	@DisplayName("스트릭이 없다면, 최초 스트릭을 생성한다.")
	@Test
	void create_initial_streak() {
		Achiever achiever = AchieverFixture.create();
		Streak streak = Streak.of(UUID.randomUUID(), UUID.randomUUID(), StreakType.DAILY, LocalDate.of(2025, 2, 5), 1);
		doReturn(achiever).when(achieverService).findById(any());
		doReturn(List.of()).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.empty()).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());
		doReturn(streak).when(streakRepository).save(any());

		sut.create(achiever.getId(), streak.getStreakType().name(), streak.getFilledDate());

		verify(streakRepository, times(1)).save(any());
	}

	@DisplayName("이미 스트릭이 있다면, 기존 스트릭의 완료 퀘스트 개수를 증가시킨다.")
	@Test
	void update_exist_streak() {
		Achiever achiever = AchieverFixture.create();
		Streak streak = Streak.of(UUID.randomUUID(), UUID.randomUUID(), StreakType.DAILY, LocalDate.now(), 2);
		doReturn(achiever).when(achieverService).findById(any());
		doReturn(List.of()).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.of(streak)).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());

		sut.create(achiever.getId(), streak.getStreakType().name(), streak.getFilledDate());

		assertThat(streak.getCompletedQuestCount()).isEqualTo(3);
	}

	@DisplayName("전날 어떠한 스트릭(DAILY, WEEKLY, MONTHLY)도 채우지 않았다면, 연속일수를 초기화한다.")
	@Test
	void initialize_streak_days() {
		Achiever achiever = Mockito.mock(Achiever.class);
		doReturn(achiever).when(achieverService).findById(any());
		doReturn(List.of()).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.empty()).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());

		sut.create(achiever.getId(), StreakType.DAILY.name(), LocalDate.now());

		verify(achiever, times(1)).updateStreakDays(eq(false));
	}

	@DisplayName("전날 스트릭(DAILY, WEEKLY, MONTHLY)을 하나라도 채웠다면, 연속일수를 갱신한다.")
	@Test
	void update_streak_days() {
		Achiever achiever = Mockito.mock(Achiever.class);
		Streak streak = Streak.of(UUID.randomUUID(), UUID.randomUUID(), StreakType.DAILY, LocalDate.now(), 2);
		doReturn(achiever).when(achieverService).findById(any());
		doReturn(List.of(streak)).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.empty()).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());

		sut.create(achiever.getId(), StreakType.DAILY.name(), LocalDate.now());

		verify(achiever, times(1)).updateStreakDays(eq(true));
	}

	@DisplayName("스트릭 목록을 조회한다.")
	@Test
	void find_All() {
		List<Streak> dailyStreaks = List.of(StreakFixture.create(StreakType.DAILY), StreakFixture.create(StreakType.DAILY));
		List<Streak> weeklyStreaks = List.of(StreakFixture.create(StreakType.WEEKLY));
		List<Streak> monthlyStreaks = List.of();

		doReturn(dailyStreaks).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDateBetween(any(), eq(StreakType.DAILY), any(), any());
		doReturn(weeklyStreaks).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDateBetween(any(), eq(StreakType.WEEKLY), any(), any());
		doReturn(monthlyStreaks).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDateBetween(any(), eq(StreakType.MONTHLY), any(), any());

		ListStreakDto actual = sut.findAll(UUID.randomUUID(), LocalDate.of(2025, 2, 6), LocalDate.of(2025, 2, 7));

		assertThat(actual.dailyStreaks().size()).isEqualTo(2);
		assertThat(actual.weeklyStreaks().size()).isEqualTo(1);
		assertThat(actual.monthlyStreaks().size()).isEqualTo(0);
	}
}
