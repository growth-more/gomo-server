package com.gomo.app.interest.domain.service;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ScoreThresholdService {

	private final int[] scoreThresholdForLevelCache = new int[101];
	private final int[] totalScoreForLevelCache = new int[101];
	private final ScoreThresholdRepository scoreThresholdRepository;

	@PostConstruct
	protected void initializeCache() {
		scoreThresholdRepository.findAll().forEach(scoreThreshold -> {
			for(int i = scoreThreshold.getMinLevel(); i<= scoreThreshold.getMaxLevel(); i++) {
				scoreThresholdForLevelCache[i] = scoreThreshold.getThreshold();

				if(i == 0) continue;
				totalScoreForLevelCache[i] = totalScoreForLevelCache[i - 1] + scoreThresholdForLevelCache[i - 1];
			}
		});
	}

	public int[] getScoreThresholdForLevel() {
		return this.scoreThresholdForLevelCache;
	}

	public int[] getTotalScoreForLevel() {
		return this.totalScoreForLevelCache;
	}

	public int[] findAll() {
		return scoreThresholdForLevelCache;
	}
}
