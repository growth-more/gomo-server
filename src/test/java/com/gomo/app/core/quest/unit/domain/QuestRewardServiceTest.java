package com.gomo.app.core.quest.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.domain.model.AssignQuestId;
import com.gomo.app.core.quest.domain.model.PointReward;
import com.gomo.app.core.quest.domain.model.QuestReward;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.ScoreReward;
import com.gomo.app.core.quest.domain.service.QuestRewardService;
import com.gomo.app.core.quest.infrastructure.InMemoryQuestRewardPolicyProvider;

@DisplayName("[Domain integration]: 퀘스트 보상 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class QuestRewardServiceTest {

	@InjectMocks
	private QuestRewardService sut;

	@Mock
	private InMemoryQuestRewardPolicyProvider inMemoryQuestRewardPolicyProvider;

	@DisplayName("일일 퀘스트 보상을 생성한다.")
	@Test
	void create_daily_quest_reward() {
		doReturn(ScoreReward.of(2)).when(inMemoryQuestRewardPolicyProvider).getScoreReward(any());
		doReturn(PointReward.of(10)).when(inMemoryQuestRewardPolicyProvider).getPointReward(any());

		QuestReward questReward = sut.create(AssignQuestId.of(UUID.randomUUID()), QuestType.DAILY);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(2);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(10);
	}

	@DisplayName("주간 퀘스트 보상을 생성한다.")
	@Test
	void create_weekly_quest_reward() {
		doReturn(ScoreReward.of(20)).when(inMemoryQuestRewardPolicyProvider).getScoreReward(any());
		doReturn(PointReward.of(150)).when(inMemoryQuestRewardPolicyProvider).getPointReward(any());

		QuestReward questReward = sut.create(AssignQuestId.of(UUID.randomUUID()), QuestType.WEEKLY);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(20);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(150);
	}

	@DisplayName("월간 퀘스트 보상을 생성한다.")
	@Test
	void create_monthly_quest_reward() {
		doReturn(ScoreReward.of(100)).when(inMemoryQuestRewardPolicyProvider).getScoreReward(any());
		doReturn(PointReward.of(1500)).when(inMemoryQuestRewardPolicyProvider).getPointReward(any());

		QuestReward questReward = sut.create(AssignQuestId.of(UUID.randomUUID()), QuestType.MONTHLY);

		assertThat(questReward.getScoreReward().getScore()).isEqualTo(100);
		assertThat(questReward.getPointReward().getAmount()).isEqualTo(1500);
	}
}
