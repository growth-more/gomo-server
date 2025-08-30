package com.gomo.app.interest.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.fixture.MajorInterestFixture;

@DisplayName("[Domain integration]: 주요 관심사 DB 접근 테스트")
public class MajorInterestRepositoryTest extends IntegrationTestBase {

	@Autowired
	MajorInterestRepository sut;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private MajorInterestRepository majorInterestRepository;

	private RegistrantId registrantId;
	private Interest interest1;
	private Interest interest2;

	@BeforeEach
	public void setUp() {
		registrantId = RegistrantId.of(UUID.randomUUID());
		interest1 = InterestFixture.create(registrantId, "interest1");
		interest2 = InterestFixture.create(registrantId, "interest2");
		interestRepository.saveAll(List.of(interest1, interest2));
	}

	@DisplayName("주요 관심사 목록의 마지막 정렬 순서를 조회한다.")
	@Test
	void count_all() {
		MajorInterest majorInterest1 = MajorInterestFixture.majorInterest(registrantId, interest1.getId(), 1);
		MajorInterest majorInterest2 = MajorInterestFixture.majorInterest(registrantId, interest2.getId(), 5);
		majorInterestRepository.saveAll(List.of(majorInterest1, majorInterest2));

		long actual = sut.findMaxDisplayOrder(registrantId);

		assertThat(actual).isEqualTo(5);
	}

	@DisplayName("특정 사용자의 주요 관심사를 모두 삭제한다.")
	@Transactional
	@Test
	void delete_major_interests() {
		MajorInterest majorInterest = MajorInterestFixture.majorInterest(registrantId, interest1.getId());
		majorInterestRepository.save(majorInterest);
		List<MajorInterest> majorInterests = sut.findAllByRegistrantIdOrderByDisplayOrder(registrantId);
		assertThat(majorInterests.size()).isNotZero();

		sut.deleteAllByRegistrantId(registrantId);
		majorInterests = sut.findAllByRegistrantIdOrderByDisplayOrder(registrantId);

		assertThat(majorInterests).isEmpty();
	}
}
