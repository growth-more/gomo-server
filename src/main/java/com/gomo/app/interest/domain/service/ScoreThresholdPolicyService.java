package com.gomo.app.interest.domain.service;

import java.util.List;

import com.gomo.app.common.DomainService;
import com.gomo.app.interest.domain.model.ScoreThresholdPolicy;
import com.gomo.app.interest.domain.repository.ScoreThresholdPolicyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ScoreThresholdPolicyService {

	private static final int POLICY_COUNT = 100;

	private final int[] scoreThresholdPolicyCache = new int[101];
	private final int[] totalScoreForLevelCache = new int[101];
	private final ScoreThresholdPolicyRepository scoreThresholdPolicyRepository;

	@PostConstruct
	protected void initializeCache() {
		List<ScoreThresholdPolicy> policies = scoreThresholdPolicyRepository.findAll();

		for(int idx = 0; idx<= POLICY_COUNT; idx++) {
			int level = policies.get(idx).getLevel();
			int threshold = policies.get(idx).getThreshold();
			scoreThresholdPolicyCache[level] = threshold;

			if(level == 0) continue;
			totalScoreForLevelCache[level] = totalScoreForLevelCache[level - 1] + scoreThresholdPolicyCache[level - 1];
		}
	}

	public int[] getScoreThresholdPolicy() {
		return this.scoreThresholdPolicyCache;
	}

	public int[] getTotalScoreForLevel() {
		return this.totalScoreForLevelCache;
	}

	public int[] findAll() {
		return scoreThresholdPolicyCache;
	}
}
