package com.gomo.app.core.quest.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.reward.PointReward;
import com.gomo.app.core.quest.domain.model.reward.ScoreReward;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.reward.policy.QuestScorePolicy;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;

@DisplayName("[Infrastructure unit]: 퀘스트 보상 프로바이더 조회 테스트")
public class InMemoryQuestRewardRepositoryTest {

	private InMemoryQuestRewardRepository sut;
	private QuestRewardPolicyRepository questRewardPolicyRepository;

	@DisplayName("프로바이더에서 퀘스트 타입별 점수 보상을 조회한다.")
	@Test
	void cache_quest_reward_score() {
		questRewardPolicyRepository = MockQuestRewardPolicyRepository.withScorePolicies(2, 20, 100);
		sut = new MockInMemoryQuestRewardRepository(questRewardPolicyRepository);

		ScoreReward dailyScore = sut.findScoreReward(QuestType.DAILY);
		ScoreReward weeklyScore = sut.findScoreReward(QuestType.WEEKLY);
		ScoreReward monthlyScore = sut.findScoreReward(QuestType.MONTHLY);

		assertThat(dailyScore.getScore()).isEqualTo(2);
		assertThat(weeklyScore.getScore()).isEqualTo(20);
		assertThat(monthlyScore.getScore()).isEqualTo(100);
	}

	@DisplayName("프로바이더에서 퀘스트 타입별 포인트 보상을 조회한다.")
	@Test
	void cache_quest_reward_point() {
		questRewardPolicyRepository = MockQuestRewardPolicyRepository.withPointPolicies(10, 150, 1500);
		sut = new MockInMemoryQuestRewardRepository(questRewardPolicyRepository);

		PointReward dailyPoint = sut.findPointReward(QuestType.DAILY);
		PointReward weeklyPoint = sut.findPointReward(QuestType.WEEKLY);
		PointReward monthlyPoint = sut.findPointReward(QuestType.MONTHLY);

		assertThat(dailyPoint.getAmount()).isEqualTo(10);
		assertThat(weeklyPoint.getAmount()).isEqualTo(150);
		assertThat(monthlyPoint.getAmount()).isEqualTo(1500);
	}

	private static class MockInMemoryQuestRewardRepository extends InMemoryQuestRewardRepository {
		public MockInMemoryQuestRewardRepository(QuestRewardPolicyRepository questRewardPolicyRepository) {
			super(questRewardPolicyRepository);
			super.initializeCaches();
		}
	}

	private static class MockQuestRewardPolicyRepository implements QuestRewardPolicyRepository {

		private List<QuestScorePolicy> scorePolicies;
		private List<QuestPointPolicy> pointPolicies;

		private MockQuestRewardPolicyRepository(List<QuestScorePolicy> scorePolicies, List<QuestPointPolicy> pointPolicies) {
			this.scorePolicies = scorePolicies;
			this.pointPolicies = pointPolicies;
		}

		private static MockQuestRewardPolicyRepository withScorePolicies(int dailyScore, int weeklyScore, int monthlyScore) {
			return new MockQuestRewardPolicyRepository(
				List.of(
					QuestScorePolicy.of(QuestType.DAILY, dailyScore),
					QuestScorePolicy.of(QuestType.WEEKLY, weeklyScore),
					QuestScorePolicy.of(QuestType.MONTHLY, monthlyScore)
				),
				List.of()
			);
		}

		private static MockQuestRewardPolicyRepository withPointPolicies(int dailyPoint, int weeklyPoint, int monthlyPoint) {
			return new MockQuestRewardPolicyRepository(
				List.of(),
				List.of(
					QuestPointPolicy.of(QuestType.DAILY, dailyPoint),
					QuestPointPolicy.of(QuestType.WEEKLY, weeklyPoint),
					QuestPointPolicy.of(QuestType.MONTHLY, monthlyPoint)
				)
			);
		}

		@Override
		public List<QuestScorePolicy> findScorePolicies() {
			return this.scorePolicies;
		}

		@Override
		public List<QuestPointPolicy> findPointPolicies() {
			return this.pointPolicies;
		}
	}
}
