package com.gomo.app.streak.integration;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.common.util.StreakDataHelper;
import com.gomo.app.streak.common.dataprovider.StreakDataProvider;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.service.StreakService;

@DisplayName("[Domain integration]: 스트릭 생성 및 조회 테스트")
public class StreakServiceTest extends IntegrationTestBase {

	@Autowired
	StreakService sut;

	@Autowired
	StreakDataProvider streakDataProvider;
	Streak dailyFirstStreak;
	Streak dailySecondStreak;

	@Autowired
	StreakDataHelper streakDataHelper;

	@BeforeEach
	void setUp() {
		dailyFirstStreak = streakDataProvider.dailyFirstStreak();
		dailySecondStreak = streakDataProvider.dailySecondStreak();
	}

	@AfterEach
	void tearDown() {
		streakDataHelper.cleanUp();
	}

	@DisplayName("스트릭이 없다면, 최초 스트릭을 생성한다.")
	@Test
	void create_initial_streak() {
		Streak streak = Streak.of(
			StreakId.of(UUID.randomUUID()),
			dailyFirstStreak.getAchieverId(),
			StreakType.DAILY,
			LocalDate.of(2025, 2, 5),
			1
		);

		Streak actual = sut.fill(streak);

		assertThat(actual.getId()).isEqualTo(streak.getId());
		assertThat(actual.getCompletedQuestCount()).isEqualTo(1);
	}

	@DisplayName("이미 스트릭이 있다면, 기존 스트릭의 완료 퀘스트 개수를 증가시킨다.")
	@Test
	void update_exist_streak() {
		Streak streak = Streak.of(
			StreakId.of(UUID.randomUUID()),
			dailyFirstStreak.getAchieverId(),
			StreakType.DAILY,
			dailyFirstStreak.getFilledDate(),
			1
		);

		Streak actual = sut.fill(streak);

		assertThat(actual.getId()).isEqualTo(dailyFirstStreak.getId());
		assertThat(actual.getCompletedQuestCount()).isEqualTo(2);
	}

	@DisplayName("타입, 날짜 별 스트릭 목록을 조회한다.")
	@Test
	void find_streaks_by_type() {
		List<Streak> streaks = sut.findAllByStreakType(
			dailyFirstStreak.getAchieverId(),
			dailyFirstStreak.getStreakType(),
			dailyFirstStreak.getFilledDate(),
			dailySecondStreak.getFilledDate()
		);

		assertThat(streaks.size()).isEqualTo(2);
	}
}
