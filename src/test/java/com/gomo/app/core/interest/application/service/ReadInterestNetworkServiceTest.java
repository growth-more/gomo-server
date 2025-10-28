package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.assertj.core.groups.Tuple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Application unit]: 관심사 네트워크 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadInterestNetworkServiceTest {

	@InjectMocks
	private ReadInterestNetworkService sut;

	@Mock
	private InterestRepository interestRepository;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@DisplayName("관심사 네트워크를 조회한다.")
	@Test
	void find_interest_network() {
		Interest expected1 = InterestFixture.create();
		Interest expected2 = InterestFixture.create();
		MajorInterest majorInterest = MajorInterestFixture.create(expected1.getRegistrantId(), expected1.getId());
		doReturn(List.of(expected1, expected2)).when(interestRepository).findAllByRegistrantId(any());
		doReturn(List.of(majorInterest)).when(majorInterestRepository).findAllByRegistrantIdAndInterestIdIn(any(), any());

		InterestRelation relation = InterestRelationFixture.create();
		doReturn(List.of(relation)).when(interestRelationRepository).findAllByRegistrantId(any());

		InterestNetworkDto actual = sut.find(relation.getRegistrantId());

		assertThat(actual.interestDtos())
			.hasSize(2)
			.extracting("id", "registrantId", "name", "logoUrl", "proficiency.level", "proficiency.score", "proficiency.totalScore", "majorInterestId")
			.containsExactly(createInterestTuple(expected1, majorInterest.getId()), createInterestTuple(expected2, null));

		assertThat(actual.relationDtos())
			.hasSize(1)
			.extracting("id", "registrantId", "parentInterestId", "childInterestId")
			.containsExactly(createRelationTuple(relation));
	}

	private @NotNull Tuple createRelationTuple(InterestRelation relation) {
		return tuple(relation.getId(), relation.getRegistrantId(), relation.getParentInterestId(),
			relation.getChildInterestId());
	}

	private @NotNull Tuple createInterestTuple(Interest interest, UUID majorInterestId) {
		return tuple(
			interest.getId(),
			interest.getRegistrantId(),
			interest.name(),
			interest.logoUrl(),
			interest.getProficiency().level(),
			interest.getProficiency().score(),
			interest.getProficiency().getTotalScore(),
			majorInterestId
		);
	}
}
