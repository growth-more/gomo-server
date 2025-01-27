package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;

@DisplayName("[Domain integration]: 관심사 DB 접근 테스트")
public class InterestRepositoryTest extends IntegrationTestBase {

	@Autowired
	InterestRepository sut;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest backend;
	private Interest spring;
	private Interest java;

	@BeforeEach
	public void setUp() {
		backend = interestDataProvider.backend();
		spring = interestDataProvider.spring();
		java = interestDataProvider.java();
	}

	@DisplayName("등록자가 등록한 모든 관심사 목록을 조회한다.")
	@Test
	void find_all() {
		List<Interest> expected = List.of(backend, spring, java);
		List<Interest> actual = sut.findAllByRegistrantId(RegistrantId.of(backend.getRegistrantId().getId()));

		assertThat(actual)
			.hasSameSizeAs(expected)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(expected);
	}
}
