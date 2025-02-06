package com.gomo.app.streak.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;

@DisplayName("[Domain unit]: 스트릭 생성 테스트")
public class StreakTest {

	private static final StreakId STREAK_ID = StreakId.of(UUID.randomUUID());
	private static final AchieverId ACHIEVER_ID = AchieverId.of(UUID.randomUUID());
	private static final LocalDate FILLED_DATE = LocalDate.of(2025, 2, 5);
	private static final int COMPLETED_QUEST_COUNT = 1;

	@DisplayName("스트릭을 생성한다.")
	@Test
	void create_streak() {
		Streak streak = Streak.of(STREAK_ID, ACHIEVER_ID, StreakType.DAILY, FILLED_DATE, COMPLETED_QUEST_COUNT);

		assertThat(streak)
			.extracting("id", "achieverId", "streakType", "filledDate", "completedQuestCount")
			.containsExactly(STREAK_ID, ACHIEVER_ID, StreakType.DAILY, FILLED_DATE, COMPLETED_QUEST_COUNT);
	}

	@DisplayName("퀘스트 완료 개수가 증가한다.")
	@Test
	void increase_completed_quest_count() {
		Streak streak = Streak.of(STREAK_ID, ACHIEVER_ID, StreakType.DAILY, FILLED_DATE, COMPLETED_QUEST_COUNT);
		streak.increaseCompletedQuestCount();

		assertThat(streak.getCompletedQuestCount()).isEqualTo(COMPLETED_QUEST_COUNT + 1);
	}

	@DisplayName("WEEKLY 타입 스트릭의 주차를 확인한다.")
	@Test
	void extract_week_of_year_from_streak() {
		Streak streak = Streak.of(STREAK_ID, ACHIEVER_ID, StreakType.WEEKLY, FILLED_DATE, COMPLETED_QUEST_COUNT);
		int weekOfYear = streak.extractWeekOfYear();

		assertThat(weekOfYear).isEqualTo(6);
	}

	@DisplayName("WEEKLY 타입이 아닌 스트릭의 주차를 확인한다.")
	@Test
	void extract_week_of_year_from_not_weekly_streak() {
		Streak streak = Streak.of(STREAK_ID, ACHIEVER_ID, StreakType.DAILY, FILLED_DATE, COMPLETED_QUEST_COUNT);

		assertThatThrownBy(streak::extractWeekOfYear)
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Only the WEEKLY type supports extracting the week from a date");
	}
}
