package com.gomo.app.core.interest.domain.repository;

import java.util.List;

import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;

public interface LevelThresholdPolicyRepository {

	List<LevelThresholdPolicy> findAll();
}
