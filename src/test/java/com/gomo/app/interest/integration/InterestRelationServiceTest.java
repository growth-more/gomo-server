package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.dataprovider.InterestRelationDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.common.util.InterestRelationDataHelper;
import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.interest.exception.InterestRelationCycleException;
import com.gomo.app.interest.exception.InterestRelationDuplicatedException;

@DisplayName("[Domain integration]: 관심사 관계선 생성 테스트")
public class InterestRelationServiceTest extends IntegrationTestBase {

	@Autowired
	InterestRelationService interestRelationService;

	@Autowired
	InterestRepository interestRepository;

	@Autowired
	InterestRelationDataProvider interestRelationDataProvider;
	InterestRelation backendToJava;

	@Autowired
	InterestDataProvider interestDataProvider;
	Interest backend;
	Interest java;
	Interest spring;

	@Autowired
	InterestRelationDataHelper interestRelationDataHelper;

	@Autowired
	InterestDataHelper interestDataHelper;

	@BeforeEach
	void setUp() {
		backendToJava = interestRelationDataProvider.backendToJava();
		backend = interestDataProvider.backend();
		java = interestDataProvider.java();
		spring = interestDataProvider.spring();
	}

	@AfterEach
	void tearDown() {
		interestRelationDataHelper.cleanUp();
		interestDataHelper.cleanUp();
	}

	@DisplayName("관심사 관계선을 생성하면 상위 관심사의 총 점수가 하위 관심사의 총 점수 만큼 증가한다.")
	@Test
	void create_relation() {
		int totalScore = backend.getProficiency().getTotalScore();
		int increment = spring.getProficiency().getTotalScore();

		interestRelationService.create(backend.getRegistrantId(), ParentInterestId.of(backend.getId()), ChildInterestId.of(spring.getId()));

		Interest updatedInterest = interestRepository.findById(backend.getId()).get();
		assertThat(updatedInterest.getProficiency().getTotalScore()).isEqualTo(totalScore + increment);
	}

	@DisplayName("관심사 관계선은 중복 생성할 수 없다.")
	@Test
	void create_duplicated_relation() {
		assertThatThrownBy(() ->
			interestRelationService.create(
				backend.getRegistrantId(),
				ParentInterestId.of(backend.getId()),
				ChildInterestId.of(java.getId())))
			.isInstanceOf(InterestRelationDuplicatedException.class)
			.hasMessageContaining("Interest relation already exists");
	}

	@DisplayName("사이클을 만드는 관심사 관계선은 생성할 수 없다.")
	@Test
	void create_cyclic_relation() {
		interestRelationService.create(backend.getRegistrantId(), ParentInterestId.of(spring.getId()), ChildInterestId.of(backend.getId()));

		assertThatThrownBy(() ->
			interestRelationService.create(
				java.getRegistrantId(),
				ParentInterestId.of(java.getId()),
				ChildInterestId.of(spring.getId())))
			.isInstanceOf(InterestRelationCycleException.class)
			.hasMessageContaining("Cycle detected in the interest network by adding this relation, which is not allowed");
	}

	@DisplayName("관심사 관계선을 삭제하면 상위 관심사의 총 점수가 하위 관심사의 총 점수 만큼 감소한다.")
	@Test
	void delete_relation() {
		int totalScore = backend.getProficiency().getTotalScore();
		int decrement = java.getProficiency().getTotalScore();

		interestRelationService.delete(backend.getRegistrantId().getId(), backendToJava.getId());

		Interest updatedInterest = interestRepository.findById(backend.getId()).get();
		assertThat(updatedInterest.getProficiency().getTotalScore()).isEqualTo(totalScore - decrement);
	}
}
