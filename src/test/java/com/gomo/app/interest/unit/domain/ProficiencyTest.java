package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.interest.domain.model.Proficiency;

@DisplayName("[Domain unit]: 숙련도 생성 및 향상 테스트")
public class ProficiencyTest {

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
		Proficiency firstEnhanced = proficiency.enhance(10, 21);
		Proficiency secondEnhanced = firstEnhanced.enhance(10, 21);

		assertThat(secondEnhanced.getScore().getScore()).isEqualTo(20);
	}

	@DisplayName("숙련도 점수가 현재 레벨 구간의 임계치와 같아지면, 점수는 0점이 되고 레벨이 1 증가한다.")
	@Test
	void proficiency_score_equal_to_threshold() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency firstEnhanced = proficiency.enhance(10, 21);
		Proficiency secondEnhanced = firstEnhanced.enhance(10, 21);
		Proficiency thirdEnhance = secondEnhanced.enhance(1, 21);

		assertThat(thirdEnhance.getLevel().getLevel()).isEqualTo(1);
		assertThat(thirdEnhance.getScore().getScore()).isEqualTo(0);
	}

	@DisplayName("숙련도 점수가 현재 레벨 구간의 임계치를 초과하면, 점수는 차액만큼 남고 레벨이 1 증가한다.")
	@Test
	void level_up_proficiency() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency firstEnhanced = proficiency.enhance(10, 21);
		Proficiency secondEnhanced = firstEnhanced.enhance(10, 21);
		Proficiency thirdEnhance = secondEnhanced.enhance(10, 21);

		assertThat(thirdEnhance.getLevel().getLevel()).isEqualTo(1);
		assertThat(thirdEnhance.getScore().getScore()).isEqualTo(9);
	}

	@DisplayName("숙련도 점수가 증가하는 만큼 총 점수도 함께 증가한다.")
	@Test
	void increase_proficiency_total_score() {
		Proficiency proficiency = Proficiency.createDefault();
		Proficiency firstEnhanced = proficiency.enhance(10, 21);
		Proficiency secondEnhanced = firstEnhanced.enhance(10, 21);
		Proficiency thirdEnhance = secondEnhanced.enhance(10, 21);

		assertThat(thirdEnhance.getTotalScore()).isEqualTo(30);
	}
}
