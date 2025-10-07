package com.gomo.app.core.interest.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;
import com.gomo.app.core.interest.domain.model.ProficiencyPolicies;
import com.gomo.app.core.interest.domain.repository.LevelThresholdPolicyRepository;
import com.gomo.app.core.interest.domain.repository.ProficiencyPolicyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
class ProficiencyPolicyRepositoryImpl implements ProficiencyPolicyRepository {

	private final LevelThresholdPolicyRepository levelThresholdPolicyRepository;
	private ProficiencyPolicies proficiencyPolicies;

	@PostConstruct
	protected void initialize() {
		List<LevelThresholdPolicy> levelThresholdPolicies = levelThresholdPolicyRepository.findAll();
		proficiencyPolicies = ProficiencyPolicies.from(levelThresholdPolicies);
	}

	@Override
	public ProficiencyPolicies getPolicies() {
		return proficiencyPolicies;
	}
}
