package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.groups.Tuple;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.ReadInterestUseCase;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.presentation.response.ListInterestResponse;
import com.gomo.app.interest.presentation.response.ReadInterestResponse;

@DisplayName("[Application unit]: 관심사 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadInterestUseCaseTest {

	@InjectMocks
	private ReadInterestUseCase sut;

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
		doReturn(expected).when(interestService).find(any(InterestId.class));
		doReturn(false).when(majorInterestRepository).existsMajorInterestByInterestId(any(InterestId.class));

		ReadInterestResponse actual = sut.find(expected.getId());

		assertThat(actual)
			.extracting("id", "registrantId", "name", "logoUrl", "level", "score", "totalScore", "isMajorInterest")
			.containsExactly(
				expected.getId().getId(),
				expected.getRegistrantId().getId(),
				expected.getName().toString(),
				expected.getLogo().getUrl(),
				expected.getProficiency().getLevel().getLevel(),
				expected.getProficiency().getScore().getScore(),
				expected.getProficiency().getTotalScore(),
				false
			);
	}

	@DisplayName("관심사 목록을 조회한다.")
	@Test
	void find_interest_list() {
		Interest expected1 = InterestFixture.create();
		Interest expected2 = InterestFixture.create();
		long isNotMajorInterest = 0L;
		long isMajorInterest = 1L;
		doReturn(List.of(expected1, expected2)).when(interestRepository).findAllByRegistrantId(any(RegistrantId.class));
		doReturn(List.of(isNotMajorInterest, isMajorInterest)).when(majorInterestRepository).existsAsMajorInterests(anyString());

		ListInterestResponse actual = sut.findAll(RegistrantId.of(expected1.getRegistrantId().getId()));

		assertThat(actual.getInterests())
			.hasSize(2)
			.extracting("id", "registrantId", "name", "logoUrl", "level", "score", "totalScore", "isMajorInterest")
			.containsExactly(createTuple(expected1, isNotMajorInterest), createTuple(expected2, isMajorInterest));
	}

	private @NotNull Tuple createTuple(Interest interest, long isMajorInterest) {
		return tuple(
			interest.getId().getId(),
			interest.getRegistrantId().getId(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			interest.getProficiency().getLevel().getLevel(),
			interest.getProficiency().getScore().getScore(),
			interest.getProficiency().getTotalScore(),
			isMajorInterest != 0
		);
	}
}
