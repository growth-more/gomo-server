package com.gomo.app.core.quest.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.QuestReward;
import com.gomo.app.core.quest.domain.model.reward.QuestRewardPolicy;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;

@DisplayName("[Domain integration]: 퀘스트 보상 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class QuestRewardProviderTest {

	@InjectMocks
	private QuestRewardProvider sut;

	@Mock
	private QuestRewardPolicyRepository questRewardPolicyRepository;

	@DisplayName("퀘스트 타입에 맞는 보상을 조회한다.")
	@Test
	void provide_daily_quest_reward() {
		List<QuestRewardPolicy> questRewardPolicies = List.of(
			QuestRewardPolicy.of(
				QuestType.DAILY,
				QuestReward.of(ScoreReward.of(1), PointReward.of(10))
			),
			QuestRewardPolicy.of(
				QuestType.WEEKLY,
				QuestReward.of(ScoreReward.of(2), PointReward.of(20))
			)
		);
		doReturn(questRewardPolicies).when(questRewardPolicyRepository).findAll();

		sut.initializeCaches();
		QuestReward questReward = sut.provide(QuestType.DAILY);

		assertThat(questReward.scoreValue()).isEqualTo(1);
		assertThat(questReward.pointValue()).isEqualTo(10);
	}
}
