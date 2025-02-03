package com.gomo.app.quest.domain.service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.PointReward;
import com.gomo.app.quest.domain.model.QuestPointPolicy;
import com.gomo.app.quest.domain.model.QuestReward;
import com.gomo.app.quest.domain.model.QuestScorePolicy;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.ScoreReward;
import com.gomo.app.quest.domain.repository.QuestRewardPolicyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class QuestRewardService {

	private final QuestRewardPolicyRepository questRewardPolicyRepository;
	private Map<QuestType, QuestScorePolicy> questScorePolicyCache;
	private Map<QuestType, QuestPointPolicy> questPointPolicyCache;

	@PostConstruct
	protected void initializeCaches() {
		questScorePolicyCache = questRewardPolicyRepository.findScorePolicies().stream()
			.collect(Collectors.toMap(QuestScorePolicy::getQuestType, Function.identity()));

		questPointPolicyCache = questRewardPolicyRepository.findPointPolicies().stream()
			.collect(Collectors.toMap(QuestPointPolicy::getQuestType, Function.identity()));
	}

	public QuestReward create(AssignQuestId assignQuestId, QuestType questType) {
		ScoreReward scoreReward = createScoreReward(questType);
		PointReward pointReward = createPointReward(questType);

		return QuestReward.of(assignQuestId, scoreReward, pointReward);
	}

	@NotNull
	private PointReward createPointReward(QuestType questType) {
		int point = questPointPolicyCache.get(questType).getPoints();
		return PointReward.of(point, TimestampGenerator.generate());
	}

	@NotNull
	private ScoreReward createScoreReward(QuestType questType) {
		int score = questScorePolicyCache.get(questType).getScore();
		return ScoreReward.of(score, TimestampGenerator.generate());
	}
}
