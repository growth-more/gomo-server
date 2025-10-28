package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.assertj.core.groups.Tuple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Application unit]: 관심사 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadInterestServiceTest {

	@InjectMocks
	private ReadInterestService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private InterestRepository interestRepository;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@DisplayName("관심사를 단건 조회한다.")
	@Test
	void find_interest() {
		Interest expected = InterestFixture.create();
		MajorInterest majorInterest = MajorInterestFixture.create(expected.getRegistrantId(), expected.getId());
		doReturn(expected).when(interestService).find(any());
		doReturn(Optional.of(majorInterest)).when(majorInterestRepository).findByInterestId(any());

		InterestDto actual = sut.find(expected.getId());

		assertThat(actual)
			.extracting("id", "registrantId", "name", "logoUrl", "majorInterestId")
			.containsExactly(
				expected.getId(),
				expected.getRegistrantId(),
				expected.getName().toString(),
				expected.getLogo().getUrl(),
				majorInterest.getId()
			);
	}

	@DisplayName("관심사 목록을 조회한다.")
	@Test
	void find_interest_list() {
		Interest expected1 = InterestFixture.create();
		Interest expected2 = InterestFixture.create();
		MajorInterest majorInterest = MajorInterestFixture.create(expected1.getRegistrantId(), expected1.getId());
		doReturn(List.of(expected1, expected2)).when(interestRepository).findAllByRegistrantId(any());
		doReturn(List.of(majorInterest)).when(majorInterestRepository).findAllByRegistrantIdAndInterestIdIn(any(), any());

		List<InterestDto> actual = sut.findAll(expected1.getRegistrantId());

		assertThat(actual)
			.hasSize(2)
			.extracting("id", "registrantId", "name", "logoUrl", "majorInterestId")
			.containsExactly(createTuple(expected1, majorInterest.getId()), createTuple(expected2, null));
	}

	@DisplayName("등록자 아이디 목록으로 관심사 목록을 조회한다.")
	@Test
	void find_interest_list_by_registrant_ids() {
		Interest expected1 = InterestFixture.create();
		Interest expected2 = InterestFixture.create();
		MajorInterest majorInterest = MajorInterestFixture.create(expected1.getRegistrantId(), expected1.getId());
		doReturn(List.of(expected1, expected2)).when(interestRepository).findAllByRegistrantIdIn(any());
		doReturn(List.of(majorInterest)).when(majorInterestRepository).findByInterestIdIn(any());

		List<InterestDto> actual = sut.findAllByRegistrantIds(Set.of(expected1.getRegistrantId(), expected2.getRegistrantId()));

		assertThat(actual)
			.hasSize(2)
			.extracting("id", "registrantId", "name", "logoUrl", "majorInterestId")
			.containsExactly(createTuple(expected1, majorInterest.getId()), createTuple(expected2, null));
	}

	private @NotNull Tuple createTuple(Interest interest, UUID majorInterestId) {
		return tuple(
			interest.getId(),
			interest.getRegistrantId(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			majorInterestId
		);
	}
}
