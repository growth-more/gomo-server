package com.gomo.app.core.streak.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.streak.fixture.AchieverFixture;

@DisplayName("[Domain Unit]: 스트릭 유지일 수 테스트")
class AchieverTest {

	@DisplayName("현재 스트릭 유지일 수가 증가한다.")
	@Test
	void increase_current_streak_days() {
		Achiever achiever = AchieverFixture.achiever(0, 0);
		achiever.updateStreakDays(true);
		assertThat(achiever.getCurrentStreakDays()).isEqualTo(1);
	}

	@DisplayName("최장 스트릭 유지일 수를 갱신한다.")
	@Test
	void update_longest_streak_days() {
		Achiever achiever = AchieverFixture.achiever(0, 0);
		achiever.updateStreakDays(true);
		assertThat(achiever.getLongestStreakDays()).isEqualTo(1);
	}

	@DisplayName("현재 스트릭 유지일 수가 초기화(1)된다.")
	@Test
	void initialize_current_streak_days() {
		Achiever achiever = AchieverFixture.achiever(3, 3);
		achiever.updateStreakDays(false);
		assertThat(achiever.getCurrentStreakDays()).isEqualTo(1);
		assertThat(achiever.getLongestStreakDays()).isEqualTo(3);
	}
}
