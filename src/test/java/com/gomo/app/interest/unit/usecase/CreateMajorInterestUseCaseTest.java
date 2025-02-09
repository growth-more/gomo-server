package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.CreateMajorInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.common.fixture.MajorInterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.exception.InterestAccessDeniedException;
import com.gomo.app.interest.presentation.response.CreateMajorInterestResponse;

@DisplayName("[Application unit]: 주요 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateMajorInterestUseCaseTest {

	@InjectMocks
	private CreateMajorInterestUseCase sut;

	@Mock
	private InterestRepository interestRepository;

	@Mock
	private MajorInterestService majorInterestService;

	@DisplayName("주요 관심사를 등록한다.")
	@Test
	void create_major_interest() {
		MajorInterest expected = MajorInterestFixture.majorInterest();
		CreateMajorInterestResponse response = CreateMajorInterestResponse.of(expected.getId());
		doReturn(Optional.of(InterestFixture.interest(expected.getRegistrantId()))).when(interestRepository).findById(any(InterestId.class));
		doReturn(expected).when(majorInterestService).create(any(Interest.class));

		CreateMajorInterestResponse actual = sut.create(expected.getRegistrantId().getId(), expected.getInterestId());

		assertThat(actual).usingRecursiveComparison().isEqualTo(response);
	}

	@DisplayName("권한 없는 접근자는 주요 관심사를 등록할 수 없다.")
	@Test
	void create_major_interest_by_unauthorized_accessor() {
		Interest interest = mock(Interest.class);
		doReturn(Optional.of(interest)).when(interestRepository).findById(any(InterestId.class));
		doThrow(InterestAccessDeniedException.class).when(interest).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.create(UUID.randomUUID(), InterestId.of(UUID.randomUUID())))
			.isInstanceOf(InterestAccessDeniedException.class);
	}
}
