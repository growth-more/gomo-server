package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.dataprovider.MajorInterestDataProvider;
import com.gomo.app.interest.domain.model.Interest;
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

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest backend;

	@BeforeEach
	public void setUp() {
		backend = interestDataProvider.backend();
		java = majorInterestDataProvider.java();
		spring = majorInterestDataProvider.spring();
	}

	@DisplayName("등록자의 모든 주요 관심사 목록을 정렬 순서에 맞게 조회한다.")
	@Test
	void find_all() {
		List<MajorInterest> expected = List.of(java, spring);
		List<MajorInterest> actual = sut.findAllByRegistrantIdOrderByDisplayOrder(RegistrantId.of(java.getRegistrantId().getId()));

		assertThat(actual)
			.hasSameSizeAs(expected)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(expected);
	}

	@DisplayName("주요 관심사 목록의 마지막 정렬 순서를 조회한다.")
	@Test
	void count_all() {
		long actual = sut.findMaxDisplayOrder(RegistrantId.of(java.getRegistrantId().getId()));

		assertThat(actual).isEqualTo(2);
	}

	@DisplayName("관심사가 아이디 목록으로 주요 관심사가 등록되어 있는지 확인한다.")
	@Test
	void exist_major_interests() throws JsonProcessingException {
		List<UUID> interestIds = List.of(backend.getId().getId(), java.getInterestId().getId(), spring.getInterestId().getId());
		String interestIdsJson = new ObjectMapper().writeValueAsString(interestIds);
		List<Long> isMajorInterests = sut.existsAsMajorInterests(interestIdsJson);

		assertThat(isMajorInterests.get(0)).isEqualTo(0);
		assertThat(isMajorInterests.get(1)).isEqualTo(1);
		assertThat(isMajorInterests.get(2)).isEqualTo(1);
	}
}
