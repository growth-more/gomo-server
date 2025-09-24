package com.gomo.app.core.interest.domain.repository;

import java.util.List;

import com.gomo.app.core.interest.domain.model.ScoreThresholdPolicy;

public interface ScoreThresholdPolicyRepository {

	List<ScoreThresholdPolicy> findAll();
}
