package com.gomo.app.core.streak.domain.service;

import static org.assertj.core.api.Assertions.*;
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

import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.repository.StreakRepository;
import com.gomo.app.core.streak.fixture.AchieverFixture;
import com.gomo.app.core.streak.fixture.StreakFixture;

@DisplayName("[Domain unit]: 스트릭 생성 및 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class StreakServiceTest {

	@InjectMocks
	private StreakService sut;

	@Mock
	private AchieverService achieverService;

	@Mock
	private StreakRepository streakRepository;

	@DisplayName("스트릭이 없다면, 최초 스트릭을 생성한다.")
	@Test
	void create_initial_streak() {
		Streak streak = Streak.of(UUID.randomUUID(), UUID.randomUUID(), StreakType.DAILY, LocalDate.of(2025, 2, 5), 1);
		doReturn(AchieverFixture.create()).when(achieverService).find(any());
		doReturn(List.of()).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.empty()).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());
		doReturn(streak).when(streakRepository).save(any());

		Streak actual = sut.fill(streak);

		verify(streakRepository, times(1)).save(any());
		assertThat(actual.getCompletedQuestCount()).isEqualTo(1);
	}

	@DisplayName("이미 스트릭이 있다면, 기존 스트릭의 완료 퀘스트 개수를 증가시킨다.")
	@Test
	void update_exist_streak() {
		Streak streak = Streak.of(UUID.randomUUID(), UUID.randomUUID(), StreakType.DAILY, LocalDate.now(), 1);
		doReturn(AchieverFixture.create()).when(achieverService).find(any());
		doReturn(List.of()).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.of(StreakFixture.create(5))).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());

		Streak actual = sut.fill(streak);

		assertThat(actual.getCompletedQuestCount()).isEqualTo(6);
	}

	@DisplayName("전날 스트릭 존재 여부에 따라 연속 유지 일수도 함께 조정한다.")
	@Test
	void adjust_streak_days() {
		Achiever achiever = Mockito.mock(Achiever.class);
		doReturn(achiever).when(achieverService).find(any());
		doReturn(List.of()).when(streakRepository).findByAchieverIdAndFilledDate(any(), any());
		doReturn(Optional.empty()).when(streakRepository).findByAchieverIdAndStreakTypeAndFilledDate(any(), any(), any());

		sut.fill(StreakFixture.create());

		verify(achiever, times(1)).updateStreakDays(eq(false));
	}

	@DisplayName("타입, 날짜 별 스트릭 목록을 조회한다.")
	@Test
	void find_streaks_by_type() {
		sut.findAllByStreakType(UUID.randomUUID(), StreakType.DAILY, LocalDate.now(), LocalDate.now());
		verify(streakRepository, times(1)).findByAchieverIdAndStreakTypeAndFilledDateBetween(any(), any(), any(), any());
	}
}
