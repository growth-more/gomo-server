package com.gomo.app.core.quest.domain.model.reward;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Domain unit]: 퀘스트 보상 생성 테스트")
public class QuestRewardTest {

	@DisplayName("퀘스트 보상을 생성한다.")
	@Test
	void create_quest_reward() {
		QuestReward questReward = QuestReward.of(ScoreReward.of(2), PointReward.of(10));

		assertThat(questReward.scoreValue()).isEqualTo(2);
		assertThat(questReward.pointValue()).isEqualTo(10);
	}
}
