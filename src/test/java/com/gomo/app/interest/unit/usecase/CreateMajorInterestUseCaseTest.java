package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.CreateMajorInterestUseCase;
import com.gomo.app.interest.application.port.dto.CreateMajorInterestDto;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.domain.service.MajorInterestService;
import com.gomo.app.interest.exception.InterestAccessDeniedException;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.fixture.MajorInterestFixture;

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
		MajorInterest majorInterest = MajorInterestFixture.majorInterest();
		CreateMajorInterestDto dto = CreateMajorInterestDto.of(majorInterest.uuid());
		doReturn(InterestFixture.create(majorInterest.getRegistrantId())).when(interestService).find(any(InterestId.class));
		doReturn(majorInterest).when(majorInterestService).create(any(Interest.class));

		CreateMajorInterestDto actual = sut.create(majorInterest.registrantUuid(), majorInterest.interestUuid());

		assertThat(actual).usingRecursiveComparison().isEqualTo(dto);
	}

	@DisplayName("권한 없는 접근자는 주요 관심사를 등록할 수 없다.")
	@Test
	void create_major_interest_by_unauthorized_accessor() {
		Interest interest = mock(Interest.class);
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doThrow(InterestAccessDeniedException.class).when(interest).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.create(UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(InterestAccessDeniedException.class);
	}
}
