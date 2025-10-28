package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.exception.MajorInterestAccessDeniedException;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.MajorInterestService;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Application unit]: 주요 관심사 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteMajorInterestServiceTest {

	@InjectMocks
	private DeleteMajorInterestService sut;

	@Mock
	private MajorInterestService majorInterestService;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@DisplayName("주요 관심사를 삭제한다.")
	@Test
	void delete_interest() {
		MajorInterest majorInterest = MajorInterestFixture.create();
		doReturn(majorInterest).when(majorInterestService).find(any());

		sut.delete(majorInterest.getRegistrantId(), majorInterest.getId());

		verify(majorInterestRepository, times(1)).delete(any(MajorInterest.class));
	}

	@DisplayName("권한 없는 접근자는 주요 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		MajorInterest majorInterest = mock(MajorInterest.class);
		doThrow(MajorInterestAccessDeniedException.class).when(majorInterest).validateAuthority(any(UUID.class));
		doReturn(majorInterest).when(majorInterestService).find(any());

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(MajorInterestAccessDeniedException.class);
	}
}
