package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.ProficiencyService;

@DisplayName("[Domain integration]: 숙련도 향상 테스트")
public class ProficiencyServiceTest extends IntegrationTestBase {

	@Autowired
	ProficiencyService sut;

	@Autowired
	InterestRepository interestRepository;

	@Autowired
	InterestDataProvider interestDataProvider;
	Interest backend;
	Interest java;

	@Autowired
	InterestDataHelper interestDataHelper;

	@BeforeEach
	void setUp() {
		backend = interestDataProvider.backend();
		java = interestDataProvider.java();
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("하위 관심사의 숙련도가 향상된다면, 모든 상위 관심사의 숙련도도 동일한 수치만큼 향상된다.")
	@Test
	void enhance_proficiency() {
		//          level:score
		// java   :   6  : 30
		// backend:   10 : 45
		sut.adjust(java.getId(), 20);

		Interest enhancedBackend = interestRepository.findById(backend.getId()).get();
		Interest enhancedJava = interestRepository.findById(java.getId()).get();

		assertThat(enhancedJava.getProficiency().getLevel().getLevel()).isEqualTo(7);
		assertThat(enhancedJava.getProficiency().getScore().getScore()).isEqualTo(10);
		assertThat(enhancedBackend.getProficiency().getLevel().getLevel()).isEqualTo(11);
		assertThat(enhancedBackend.getProficiency().getScore().getScore()).isEqualTo(5);
	}

	@DisplayName("하위 관심사의 숙련도가 감소하면, 모든 상위 관심사의 숙련도도 동일한 수치만큼 감소한다.")
	@Test
	void reduce_proficiency() {
		//          level:score
		// java   :   6  : 30
		// backend:   10 : 45
		sut.adjust(java.getId(), -50);

		Interest enhancedBackend = interestRepository.findById(backend.getId()).get();
		Interest enhancedJava = interestRepository.findById(java.getId()).get();

		assertThat(enhancedJava.getProficiency().getLevel().getLevel()).isEqualTo(5);
		assertThat(enhancedJava.getProficiency().getScore().getScore()).isEqualTo(20);
		assertThat(enhancedBackend.getProficiency().getLevel().getLevel()).isEqualTo(9);
		assertThat(enhancedBackend.getProficiency().getScore().getScore()).isEqualTo(35);
		assertThat(enhancedBackend.getProficiency().getScoreThreshold()).isEqualTo(40);
	}
}
