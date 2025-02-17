package com.gomo.app.interest.domain.repository;

import java.util.List;

import com.gomo.app.interest.domain.model.ScoreThresholdPolicy;

public interface ScoreThresholdPolicyRepository {

	List<ScoreThresholdPolicy> findAll();
}
