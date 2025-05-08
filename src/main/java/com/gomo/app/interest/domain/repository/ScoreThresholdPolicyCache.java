package com.gomo.app.interest.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.ScoreThresholdPolicy;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
@Getter
public class ScoreThresholdPolicyCache {

	private static final int POLICY_COUNT = 100;

	private final int[] scoreThresholdPerLevel;
	private final int[] totalScoreForLevel;
	private final ScoreThresholdPolicyRepository scoreThresholdPolicyRepository;

	public ScoreThresholdPolicyCache(ScoreThresholdPolicyRepository scoreThresholdPolicyRepository) {
		this.scoreThresholdPerLevel = new int[101];
		this.totalScoreForLevel = new int[101];
		this.scoreThresholdPolicyRepository = scoreThresholdPolicyRepository;
	}

	@PostConstruct
	protected void initializeCache() {
		List<ScoreThresholdPolicy> policies = scoreThresholdPolicyRepository.findAll();

		for(int idx = 0; idx<= POLICY_COUNT; idx++) {
			int level = policies.get(idx).getLevel();
			int threshold = policies.get(idx).getThreshold();
			scoreThresholdPerLevel[level] = threshold;

			if(level == 0) continue;
			totalScoreForLevel[level] = totalScoreForLevel[level - 1] + scoreThresholdPerLevel[level - 1];
		}
	}
}
