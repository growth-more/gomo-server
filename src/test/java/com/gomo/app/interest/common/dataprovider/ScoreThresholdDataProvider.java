package com.gomo.app.interest.common.dataprovider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.ScoreThresholdPolicy;
import com.gomo.app.interest.domain.repository.ScoreThresholdPolicyRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 레벨 구간 별 임계점수 데이터를 제공한다.
 * leve: 0~100
 * threshold: 40~10000
 */
@Component
public class ScoreThresholdDataProvider {

	@Autowired
	ScoreThresholdPolicyRepository scoreThresholdPolicyRepository;

	List<ScoreThresholdPolicy> policies;

	@PostConstruct
	public void initialize() {
		policies = scoreThresholdPolicyRepository.findAll();
	}

	public List<ScoreThresholdPolicy> scoreThresholds() {
		return this.policies;
	}
}
