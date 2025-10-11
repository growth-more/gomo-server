package com.gomo.app.core.quest.domain.model.reward;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Domain unit]: 퀘스트 보상 생성 테스트")
public class QuestRewardTest {

	@DisplayName("퀘스트 보상을 생성한다.")
	@Test
	void create_quest_reward() {
		QuestReward questReward = QuestReward.of(
			UUID.randomUUID(),
			ScoreReward.of(2),
			PointReward.of(10)
		);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(2);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(10);
	}
}
