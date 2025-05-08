package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.ReadMajorInterestUseCase;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.fixture.MajorInterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.response.ListMajorInterestResponse;

@DisplayName("[Application unit]: 주요 관심사 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class ReadMajorInterestUseCaseTest {

	@InjectMocks
	private ReadMajorInterestUseCase sut;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("주요 관심사 목록을 정렬 순서에 맞게 조회한다.")
	@Test
	void find_interest_by_display_order() {
		MajorInterest expected1 = MajorInterestFixture.majorInterest();
		MajorInterest expected2 = MajorInterestFixture.majorInterest();
		List<Interest> interests = getInterests(expected1, expected2);
		doReturn(interests).when(interestRepository).findAllByIdIsIn(any());
		doReturn(List.of(expected1, expected2)).when(majorInterestRepository).findAllByRegistrantIdOrderByDisplayOrder(any(RegistrantId.class));

		ListMajorInterestResponse response = sut.findAll(expected1.getRegistrantId().getId());

		assertThat(response.getMajorInterests())
			.hasSize(2)
			.extracting("id", "interestId", "name")
			.containsExactly(
				tuple(expected1.getId().getId(), expected1.getInterestId().getId(), "interest1"),
				tuple(expected2.getId().getId(), expected2.getInterestId().getId(), "interest2")
			);
	}

	private List<Interest> getInterests(MajorInterest expected1, MajorInterest expected2) {
		return List.of(
			InterestFixture.create(expected1.getInterestId(), "interest1"),
			InterestFixture.create(expected2.getInterestId(), "interest2")
		);
	}
}
