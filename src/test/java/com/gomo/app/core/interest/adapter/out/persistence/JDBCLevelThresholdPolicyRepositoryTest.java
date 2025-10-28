package com.gomo.app.core.interest.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain integration]: 레벨별 임계 점수 정책 DB 통합 테스트")
@IntegrationTest
class JDBCLevelThresholdPolicyRepositoryTest {

	@Autowired
	JDBCLevelThresholdPolicyRepository repository;

	@DisplayName("레벨별 임계 점수 정책 목록을 조회한다")
	@Test
	void find_all_policies() {
		List<LevelThresholdPolicy> result = repository.findAll();
		assertThat(result).hasSize(101);
	}
}
