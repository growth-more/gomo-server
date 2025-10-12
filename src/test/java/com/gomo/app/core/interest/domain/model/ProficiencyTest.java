package com.gomo.app.core.interest.domain.model;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Domain unit]: 숙련도 생성 및 향상 테스트")
public class ProficiencyTest {

	@DisplayName("숙련도의 기본 점수 및 총 점수는 0점, 기본 레벨은 0이다.")
	@Test
	void create_proficiency() {
		Proficiency proficiency = Proficiency.createDefault();

		assertThat(proficiency.level()).isEqualTo(0);
		assertThat(proficiency.score()).isEqualTo(0);
		assertThat(proficiency.getTotalScore()).isEqualTo(0);
	}

	@DisplayName("숙련도를 조정한다.")
	@Test
	void adjust_proficiency() {
		ProficiencyCalculator mockPolicies = mock(ProficiencyCalculator.class);
		Proficiency proficiency = Proficiency.createDefault();
		proficiency.adjust(10, mockPolicies);

		verify(mockPolicies, times(1)).calculate(anyInt());
	}
}
