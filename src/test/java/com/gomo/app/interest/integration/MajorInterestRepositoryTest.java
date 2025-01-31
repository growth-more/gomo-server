package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.MajorInterestDataProvider;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;

@DisplayName("[Domain integration]: 주요 관심사 DB 접근 테스트")
public class MajorInterestRepositoryTest extends IntegrationTestBase {

	@Autowired
	MajorInterestRepository sut;

	@Autowired
	private MajorInterestDataProvider majorInterestDataProvider;
	private MajorInterest java;
	private MajorInterest spring;

	@BeforeEach
	public void setUp() {
		java = majorInterestDataProvider.java();
		spring = majorInterestDataProvider.spring();
	}

	@DisplayName("등록자가 등록한 모든 주요 관심사 목록을 정렬 순서에 맞게 조회한다.")
	@Test
	void find_all() {
		List<MajorInterest> expected = List.of(java, spring);
		List<MajorInterest> actual = sut.findAllByRegistrantIdOrderByDisplayOrder(RegistrantId.of(java.getRegistrantId().getId()));

		assertThat(actual)
			.hasSameSizeAs(expected)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(expected);
	}

	@DisplayName("등록자가 등록한 모든 주요 관심사의 개수를 조회한다.")
	@Test
	void count_all() {
		long actual = sut.countAllByRegistrantId(RegistrantId.of(java.getRegistrantId().getId()));

		assertThat(actual).isEqualTo(2);
	}
}
