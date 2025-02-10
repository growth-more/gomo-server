package com.gomo.app.quest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.QuestReward;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.service.QuestRewardService;

@DisplayName("[Domain integration]: 퀘스트 보상 생성 테스트")
public class QuestRewardServiceTest extends IntegrationTestBase {

	@Autowired
	QuestRewardService sut;

	@DisplayName("일일 퀘스트 보상을 생성한다.")
	@Test
	void create_daily_quest_reward() {
		QuestReward questReward = sut.create(AssignQuestId.of(UUID.randomUUID()), QuestType.DAILY);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(2);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(10);
	}

	@DisplayName("주간 퀘스트 보상을 생성한다.")
	@Test
	void create_weekly_quest_reward() {
		QuestReward questReward = sut.create(AssignQuestId.of(UUID.randomUUID()), QuestType.WEEKLY);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(20);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(150);
	}

	@DisplayName("월간 퀘스트 보상을 생성한다.")
	@Test
	void create_monthly_quest_reward() {
		QuestReward questReward = sut.create(AssignQuestId.of(UUID.randomUUID()), QuestType.MONTHLY);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(100);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(1500);
	}
}
