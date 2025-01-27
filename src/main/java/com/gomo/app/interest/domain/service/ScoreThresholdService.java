package com.gomo.app.interest.domain.service;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.interest.domain.model.Level;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ScoreThresholdService {

	private final int[] scoreThresholdCache = new int[101];
	private final ScoreThresholdRepository scoreThresholdRepository;

	@PostConstruct
	protected void initializeCache() {
		scoreThresholdRepository.findAll().forEach(scoreThreshold -> {
			for(int i = scoreThreshold.getMinLevel(); i<= scoreThreshold.getMaxLevel(); i++) {
				scoreThresholdCache[i] = scoreThreshold.getThreshold();
			}
		});
	}

	public int findScoreThreshold(Level level) {
		return scoreThresholdCache[level.getLevel()];
	}

	public int[] findAll() {
		return scoreThresholdCache;
	}
}
