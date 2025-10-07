package com.gomo.app.core.interest.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;

class LevelThresholdPolicyRepositoryImplTest extends IntegrationTestBase {

	@Autowired
	LevelThresholdPolicyRepositoryImpl repository;

	@Test
	@DisplayName("레벨별 임계 점수 정책 목록을 조회한다")
	void find_all_policies() {
		List<LevelThresholdPolicy> result = repository.findAll();
		assertThat(result).hasSize(101);
	}
}
