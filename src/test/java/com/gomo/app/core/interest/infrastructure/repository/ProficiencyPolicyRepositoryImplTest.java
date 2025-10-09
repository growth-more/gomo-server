package com.gomo.app.core.interest.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;
import com.gomo.app.core.interest.domain.model.ProficiencyPolicies;
import com.gomo.app.core.interest.domain.repository.LevelThresholdPolicyRepository;
import com.gomo.app.test.IntegrationTest;

@DisplayName("[Domain integration]: 숙련도 정책 초기화 테스트")
@IntegrationTest
public class ProficiencyPolicyRepositoryImplTest {

	@InjectMocks
	private ProficiencyPolicyRepositoryImpl sut;

	@Mock
	private LevelThresholdPolicyRepository levelThresholdPolicyRepository;

	private LevelThresholdPolicy policy(int level, int threshold) {
		return LevelThresholdPolicy.of(level, threshold);
	}

	@DisplayName("초기화 시 DB를 이용해 내부적으로 숙련도 정책을를 생성한다")
	@Test
	void initialize_should_fetch_policies() {
		List<LevelThresholdPolicy> fakePolicies = List.of(
			policy(0, 10),
			policy(1, 20)
		);
		doReturn(fakePolicies).when(levelThresholdPolicyRepository).findAll();

		sut.initialize();
		ProficiencyPolicies result = sut.getPolicies();

		assertThat(result).isNotNull();
		verify(levelThresholdPolicyRepository, times(1)).findAll();
	}
}
