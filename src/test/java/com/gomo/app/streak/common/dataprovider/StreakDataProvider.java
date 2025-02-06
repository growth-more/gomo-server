package com.gomo.app.streak.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.repository.StreakRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 스트릭 데이터를 제공한다.
 * @ 총 세 가지 데이터가 존재한다.
 * @ 1. daily(2025-01-18)
 * @ 2. daily(2025-02-06)
 * @ 3. weekly(2025-01-20)
 */
@Component
public class StreakDataProvider {

	private static final String DAILY_FIRST_STREAK_ID = "203ece27-d868-11ef-824e-338baee5b682";
	private static final String DAILY_SECOND_STREAK_ID = "fa1cf1b5-e3d3-11ef-9c0c-5f1ac1d71b42";
	private static final String WEEKLY_STREAK_ID = "febad463-d868-11ef-826f-395aa04e34d3";
	private Streak dailyFirstStreak;
	private Streak dailySecondStreak;
	private Streak weeklyStreak;

	@Autowired
	private StreakRepository streakRepository;

	@PostConstruct
	public void initialize() {
		dailyFirstStreak = streakRepository.findById(StreakId.of(UUID.fromString(DAILY_FIRST_STREAK_ID)))
			.orElseThrow(() -> new IllegalStateException("StreakDataProvider 초기화 실패: DAILY_FIRST_STREAK_ID에 해당하는 Streak이 없습니다."));

		dailySecondStreak = streakRepository.findById(StreakId.of(UUID.fromString(DAILY_SECOND_STREAK_ID)))
			.orElseThrow(() -> new IllegalStateException("StreakDataProvider 초기화 실패: DAILY_SECOND_STREAK_ID에 해당하는 Streak이 없습니다."));

		weeklyStreak = streakRepository.findById(StreakId.of(UUID.fromString(WEEKLY_STREAK_ID)))
			.orElseThrow(() -> new IllegalStateException("StreakDataProvider 초기화 실패: WEEKLY_STREAK_ID에 해당하는 Streak이 없습니다."));
	}

	public Streak dailyFirstStreak() {
		return dailyFirstStreak;
	}

	public Streak dailySecondStreak() {
		return dailySecondStreak;
	}

	public Streak weeklyStreak() {
		return weeklyStreak;
	}
}
