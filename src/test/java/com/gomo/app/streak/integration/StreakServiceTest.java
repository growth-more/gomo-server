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
import com.gomo.app.streak.domain.model.Achiever;
import com.gomo.app.streak.domain.model.AchieverId;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakId;
import com.gomo.app.streak.domain.model.StreakType;
import com.gomo.app.streak.domain.repository.AchieverRepository;
import com.gomo.app.streak.domain.repository.StreakRepository;
import com.gomo.app.streak.domain.service.StreakService;
import com.gomo.app.streak.fixture.StreakFixture;

@DisplayName("[Domain integration]: 스트릭 생성 및 조회 테스트")
public class StreakServiceTest extends IntegrationTestBase {

	@Autowired
	StreakService sut;

	@Autowired
	AchieverRepository achieverRepository;

	@Autowired
	StreakRepository streakRepository;
	UUID achieverId;
	Streak dailyStreak1;
	Streak dailyStreak2;

	@BeforeEach
	public void setUp() {
		achieverId = UUID.randomUUID();
		achieverRepository.save(Achiever.of(AchieverId.of(achieverId)));
		dailyStreak1 = StreakFixture.streak(achieverId, StreakType.DAILY, LocalDate.of(2025, 1, 18));
		dailyStreak2 = StreakFixture.streak(achieverId, StreakType.DAILY, LocalDate.of(2025, 2, 6));
		streakRepository.saveAll(List.of(dailyStreak1, dailyStreak2));
	}

	@AfterEach
	void tearDown() {
		streakRepository.deleteAllInBatch();
	}

	@DisplayName("스트릭이 없다면, 최초 스트릭을 생성한다.")
	@Test
	void create_initial_streak() {
		Streak streak = Streak.of(
			StreakId.of(UUID.randomUUID()),
			AchieverId.of(achieverId),
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
			AchieverId.of(achieverId),
			StreakType.DAILY,
			dailyStreak1.getFilledDate(),
			1
		);

		Streak actual = sut.fill(streak);

		assertThat(actual.getId()).isEqualTo(dailyStreak1.getId());
		assertThat(actual.getCompletedQuestCount()).isEqualTo(2);
	}

	@DisplayName("스트릭 생성 시, 전날 스트릭이 존재 여부에 따라 유지 일수도 함께 조정한다.")
	@Test
	void adjust_streak_days() {
		Streak streak = Streak.of(
			StreakId.of(UUID.randomUUID()),
			AchieverId.of(achieverId),
			StreakType.DAILY,
			LocalDate.now(),
			1
		);
		Streak priorStreak = StreakFixture.streak(achieverId, StreakType.DAILY, LocalDate.now().minusDays(1));
		sut.fill(priorStreak);

		sut.fill(streak);
		Achiever achiever = achieverRepository.findById(AchieverId.of(achieverId)).get();

		assertThat(achiever.getCurrentStreakDays()).isEqualTo(2);
	}

	@DisplayName("타입, 날짜 별 스트릭 목록을 조회한다.")
	@Test
	void find_streaks_by_type() {
		List<Streak> streaks = sut.findAllByStreakType(
			AchieverId.of(achieverId),
			StreakType.DAILY,
			dailyStreak1.getFilledDate(),
			dailyStreak2.getFilledDate()
		);

		assertThat(streaks.size()).isEqualTo(2);
	}

	@DisplayName("특정 사용자의 스트릭을 모두 삭제한다.")
	@Test
	void delete_all_streaks() {
		sut.deleteAllByAchieverId(AchieverId.of(achieverId));
		
		assertThat(streakRepository.count()).isEqualTo(0);
	}
}
