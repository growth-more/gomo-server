package com.gomo.app.interest.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
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
import com.gomo.app.core.interest.application.usecase.ReadInterestUseCase;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.fixture.MajorInterestFixture;

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
		MajorInterest majorInterest = MajorInterestFixture.majorInterest(expected.getRegistrantId(), expected.getId());
		doReturn(expected).when(interestService).find(any(InterestId.class));
		doReturn(Optional.of(majorInterest)).when(majorInterestRepository).findByInterestId(any());

		InterestDto actual = sut.find(expected.uuid());

		assertThat(actual)
			.extracting("id", "registrantId", "name", "logoUrl", "majorInterestId")
			.containsExactly(
				expected.getId().getId(),
				expected.getRegistrantId().getId(),
				expected.getName().toString(),
				expected.getLogo().getUrl(),
				majorInterest.uuid()
			);
	}

	@DisplayName("관심사 목록을 조회한다.")
	@Test
	void find_interest_list() {
		Interest expected1 = InterestFixture.create();
		Interest expected2 = InterestFixture.create();
		MajorInterest majorInterest = MajorInterestFixture.majorInterest(expected1.getRegistrantId(), expected1.getId());
		doReturn(List.of(expected1, expected2)).when(interestRepository).findAllByRegistrantId(any(RegistrantId.class));
		doReturn(List.of(majorInterest)).when(majorInterestRepository).findAllByRegistrantIdAndInterestIdIn(any(), any());

		List<InterestDto> actual = sut.findAll(expected1.getRegistrantId().getId());

		assertThat(actual)
			.hasSize(2)
			.extracting("id", "registrantId", "name", "logoUrl", "majorInterestId")
			.containsExactly(createTuple(expected1, majorInterest.uuid()), createTuple(expected2, null));
	}

	private @NotNull Tuple createTuple(Interest interest, UUID majorInterestId) {
		return tuple(
			interest.getId().getId(),
			interest.getRegistrantId().getId(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			majorInterestId
		);
	}
}
