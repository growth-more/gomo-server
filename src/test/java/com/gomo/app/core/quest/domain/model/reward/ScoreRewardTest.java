package com.gomo.app.core.quest.domain.model.reward;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Domain unit]: 숙련도 점수 보상 생성 테스트")
public class ScoreRewardTest {

	@DisplayName("숙련도 점수 보상을 생성한다.")
	@Test
	void create_score_reward() {
		ScoreReward scoreReward = ScoreReward.of(2);

		assertThat(scoreReward.score()).isEqualTo(2);
	}
}
