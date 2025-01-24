package com.gomo.app.interest.domain.service;

import java.util.List;

import com.gomo.app.common.domain.DomainService;
import com.gomo.app.interest.domain.model.Level;
import com.gomo.app.interest.domain.model.ScoreThreshold;
import com.gomo.app.interest.domain.repository.ScoreThresholdRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ScoreThresholdService {

	private final List<ScoreThreshold> scoreThresholdCache;
	private final ScoreThresholdRepository scoreThresholdRepository;

	protected void initializeCache() {}

	public int findScoreThreshold(Level level) {
		return 0;
	}
}
