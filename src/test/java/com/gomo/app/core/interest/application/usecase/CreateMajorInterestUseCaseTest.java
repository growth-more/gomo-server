package com.gomo.app.core.interest.application.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.domain.service.MajorInterestService;
import com.gomo.app.core.interest.exception.InterestAccessDeniedException;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Application unit]: 주요 관심사 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateMajorInterestUseCaseTest {

	@InjectMocks
	private CreateMajorInterestUseCase sut;

	@Mock
	private InterestService interestService;

	@Mock
	private MajorInterestService majorInterestService;

	@DisplayName("주요 관심사를 등록한다.")
	@Test
	void create_major_interest() {
		MajorInterest majorInterest = MajorInterestFixture.create();
		doReturn(InterestFixture.create(majorInterest.getRegistrantId())).when(interestService).find(any());
		doReturn(majorInterest).when(majorInterestService).create(any(Interest.class));

		UUID actual = sut.create(majorInterest.getRegistrantId(), majorInterest.getInterestId());

		assertThat(actual).isEqualTo(majorInterest.getId());
	}

	@DisplayName("권한 없는 접근자는 주요 관심사를 등록할 수 없다.")
	@Test
	void create_major_interest_by_unauthorized_accessor() {
		Interest interest = mock(Interest.class);
		doReturn(interest).when(interestService).find(any());
		doThrow(InterestAccessDeniedException.class).when(interest).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.create(UUID.randomUUID(), UUID.randomUUID())).isInstanceOf(InterestAccessDeniedException.class);
	}
}
