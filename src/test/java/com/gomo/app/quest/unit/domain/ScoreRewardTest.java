package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.quest.domain.model.ScoreReward;

@DisplayName("[Domain unit]: 숙련도 점수 보상 생성 테스트")
public class ScoreRewardTest {

	@DisplayName("숙련도 점수 보상을 생성한다.")
	@Test
	void create_score_reward() {
		ScoreReward scoreReward = ScoreReward.of(2, 1L);

		assertThat(scoreReward.getScore()).isEqualTo(2);
	}
}
