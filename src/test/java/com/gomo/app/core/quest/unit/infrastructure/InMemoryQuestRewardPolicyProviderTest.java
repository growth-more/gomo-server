package com.gomo.app.core.quest.unit.infrastructure;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.PointReward;
import com.gomo.app.core.quest.domain.model.QuestPointPolicy;
import com.gomo.app.core.quest.domain.model.QuestScorePolicy;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.ScoreReward;
import com.gomo.app.core.quest.domain.repository.QuestRewardPolicyRepository;
import com.gomo.app.core.quest.infrastructure.InMemoryQuestRewardPolicyProvider;

@DisplayName("[Infrastructure unit]: 퀘스트 보상 프로바이더 조회 테스트")
public class InMemoryQuestRewardPolicyProviderTest {

	private InMemoryQuestRewardPolicyProvider sut;
	private QuestRewardPolicyRepository questRewardPolicyRepository;

	@DisplayName("프로바이더에서 퀘스트 타입별 점수 보상을 조회한다.")
	@Test
	void cache_quest_reward_score() {
		questRewardPolicyRepository = MockQuestRewardPolicyRepository.withScorePolicies(2, 20, 100);
		sut = new MockInMemoryQuestRewardPolicyProvider(questRewardPolicyRepository);

		ScoreReward dailyScore = sut.getScoreReward(QuestType.DAILY);
		ScoreReward weeklyScore = sut.getScoreReward(QuestType.WEEKLY);
		ScoreReward monthlyScore = sut.getScoreReward(QuestType.MONTHLY);

		assertThat(dailyScore.getScore()).isEqualTo(2);
		assertThat(weeklyScore.getScore()).isEqualTo(20);
		assertThat(monthlyScore.getScore()).isEqualTo(100);
	}

	@DisplayName("프로바이더에서 퀘스트 타입별 포인트 보상을 조회한다.")
	@Test
	void cache_quest_reward_point() {
		questRewardPolicyRepository = MockQuestRewardPolicyRepository.withPointPolicies(10, 150, 1500);
		sut = new MockInMemoryQuestRewardPolicyProvider(questRewardPolicyRepository);

		PointReward dailyPoint = sut.getPointReward(QuestType.DAILY);
		PointReward weeklyPoint = sut.getPointReward(QuestType.WEEKLY);
		PointReward monthlyPoint = sut.getPointReward(QuestType.MONTHLY);

		assertThat(dailyPoint.getAmount()).isEqualTo(10);
		assertThat(weeklyPoint.getAmount()).isEqualTo(150);
		assertThat(monthlyPoint.getAmount()).isEqualTo(1500);
	}

	private static class MockInMemoryQuestRewardPolicyProvider extends InMemoryQuestRewardPolicyProvider {
		public MockInMemoryQuestRewardPolicyProvider(QuestRewardPolicyRepository questRewardPolicyRepository) {
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
