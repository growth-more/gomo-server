package com.gomo.app.interest.domain.repository;

import java.util.List;

import com.gomo.app.interest.domain.model.ScoreThreshold;

public interface ScoreThresholdRepository {

	List<ScoreThreshold> findAll();
}
