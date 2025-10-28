package com.gomo.app.core.interest.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.interest.domain.exception.ProficiencyAdjustFailureException;

@DisplayName("[Domain unit]: 숙련도 정책 연산 테스트")
class ProficiencyCalculatorTest {

	@Test
	@DisplayName("총점이 0이라면 숙련도는 첫 번째 레벨의 0점이다.")
	void calculate_proficiency_with_zero_total_score() {
		List<LevelThresholdPolicy> levelThresholdPolicies = List.of(
			LevelThresholdPolicy.of(1, 10)
		);
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(levelThresholdPolicies);

		Proficiency actual = proficiencyCalculator.calculate(0);

		assertThat(actual.level()).isEqualTo(1);
		assertThat(actual.score()).isEqualTo(0);
		assertThat(actual.getTotalScore()).isEqualTo(0);
	}

	@Test
	@DisplayName("숙련도 점수는 임계값에 도달할 때까지 증가한다.")
	void calculate_proficiency_with_total_score() {
		List<LevelThresholdPolicy> levelThresholdPolicies = List.of(
			LevelThresholdPolicy.of(1, 10)
		);
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(levelThresholdPolicies);

		Proficiency actual = proficiencyCalculator.calculate(5);

		assertThat(actual.level()).isEqualTo(1);
		assertThat(actual.score()).isEqualTo(5);
		assertThat(actual.getTotalScore()).isEqualTo(5);
	}

	@Test
	@DisplayName("총점이 정확히 다음 레벨 경계값일 때, 숙련도는 다음 레벨의 0점이 된다.")
	void calculate_proficiency_at_level_boundary() {
		List<LevelThresholdPolicy> levelThresholdPolicies = List.of(
			LevelThresholdPolicy.of(1, 10),
			LevelThresholdPolicy.of(2, 20),
			LevelThresholdPolicy.of(3, 30)
		);
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(levelThresholdPolicies);

		Proficiency actual = proficiencyCalculator.calculate(10);

		assertThat(actual.level()).isEqualTo(2);
		assertThat(actual.score()).isEqualTo(0);
		assertThat(actual.getTotalScore()).isEqualTo(10);
	}

	@Test
	@DisplayName("총점이 다음 레벨의 임계점을 초과하면, 숙련도는 해당 레벨과 초과 점수로 계산된다.")
	void calculate_proficiency_in_level_interval() {
		List<LevelThresholdPolicy> levelThresholdPolicies = List.of(
			LevelThresholdPolicy.of(1, 10),
			LevelThresholdPolicy.of(2, 20),
			LevelThresholdPolicy.of(3, 30),
			LevelThresholdPolicy.of(4, 40)
		);
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(levelThresholdPolicies);

		Proficiency actual = proficiencyCalculator.calculate(31);

		assertThat(actual.level()).isEqualTo(3);
		assertThat(actual.score()).isEqualTo(1);
		assertThat(actual.getTotalScore()).isEqualTo(31);
	}

	@Test
	@DisplayName("총점이 최대 누적 점수(최고 레벨)를 초과하면, 점수는 계속해서 누적된다")
	void calculate_proficiency_at_highest_level() {
		List<LevelThresholdPolicy> levelThresholdPolicies = List.of(
			LevelThresholdPolicy.of(1, 10),
			LevelThresholdPolicy.of(2, 20),
			LevelThresholdPolicy.of(3, 30)
		);
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(levelThresholdPolicies);

		Proficiency actual = proficiencyCalculator.calculate(100);

		assertThat(actual.level()).isEqualTo(3);
		assertThat(actual.score()).isEqualTo(70);
		assertThat(actual.getTotalScore()).isEqualTo(100);
	}

	@Test
	@DisplayName("총점은 음수일 수 없다.")
	void calculate_proficiency_with_negative_total_score() {
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(List.of());
		assertThatThrownBy(() -> proficiencyCalculator.calculate(-1)).isInstanceOf(ProficiencyAdjustFailureException.class);
	}

	@Test
	@DisplayName("정책 목록은 항상 초기화되어 있어야 한다.")
	void initialize_policies() {
		ProficiencyCalculator proficiencyCalculator = ProficiencyCalculator.from(List.of());
		assertThatThrownBy(() -> proficiencyCalculator.calculate(10)).isInstanceOf(IllegalStateException.class);
	}
}
