package com.gomo.app.core.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.interest.domain.model.Proficiency;
import com.gomo.app.core.interest.exception.ProficiencyAdjustFailureException;
import com.gomo.app.core.interest.exception.code.ProficiencyErrorCode;

@DisplayName("[Domain unit]: 숙련도 생성 및 향상 테스트")
public class ProficiencyTest {

	private final int[] totalScoreForLevel = {0, 40, 80, 120};
	private final int[] scoreThresholdsPerLevel = {0, 40, 40, 40};

	@DisplayName("숙련도의 기본 점수 및 총 점수는 0점, 기본 레벨은 0이다.")
	@Test
	void create_proficiency() {
		Proficiency proficiency = Proficiency.createDefault();

		assertThat(proficiency.getLevel().getLevel()).isEqualTo(0);
		assertThat(proficiency.getScore().getScore()).isEqualTo(0);
		assertThat(proficiency.getTotalScore()).isEqualTo(0);
	}

	@DisplayName("숙련도 점수는 임계치에 도달하기 전까지 증가한다.")
	@Test
	void increase_proficiency_score() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency adjustedProficiency = proficiency.adjust(20, totalScoreForLevel, scoreThresholdsPerLevel);

		assertThat(adjustedProficiency.getScore().getScore()).isEqualTo(20);
	}

	@DisplayName("숙련도 점수가 현재 레벨 구간의 임계치와 같아지면, 점수는 0점이 되고 레벨이 1 증가한다.")
	@Test
	void proficiency_score_equal_to_threshold() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency adjustedProficiency = proficiency.adjust(40, totalScoreForLevel, scoreThresholdsPerLevel);

		assertThat(adjustedProficiency.getLevel().getLevel()).isEqualTo(1);
		assertThat(adjustedProficiency.getScore().getScore()).isEqualTo(0);
	}

	@DisplayName("숙련도 점수가 현재 레벨 구간의 임계치를 초과하면, 점수는 차액만큼 남고 레벨이 1 증가한다.")
	@Test
	void level_up_proficiency() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency adjustedProficiency = proficiency.adjust(50, totalScoreForLevel, scoreThresholdsPerLevel);

		assertThat(adjustedProficiency.getLevel().getLevel()).isEqualTo(1);
		assertThat(adjustedProficiency.getScore().getScore()).isEqualTo(10);
	}

	@DisplayName("숙련도 점수가 증가하는 만큼 총 점수도 함께 증가한다.")
	@Test
	void increase_proficiency_total_score() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency adjustedProficiency = proficiency.adjust(20, totalScoreForLevel, scoreThresholdsPerLevel);

		assertThat(adjustedProficiency.getTotalScore()).isEqualTo(20);
	}

	@DisplayName("총 점수는 음수가 될 수 없다.")
	@Test
	void proficiency_total_score_not_negative() {
		Proficiency proficiency = Proficiency.createDefault();

		assertThatThrownBy(() -> proficiency.adjust(-10, totalScoreForLevel, scoreThresholdsPerLevel))
			.isInstanceOf(ProficiencyAdjustFailureException.class)
			.hasMessageContaining(ProficiencyErrorCode.NEGATIVE_TOTAL_SCORE.getMessage());
	}
}
