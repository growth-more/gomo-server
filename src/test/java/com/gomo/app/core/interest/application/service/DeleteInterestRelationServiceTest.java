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

import com.gomo.app.core.interest.domain.exception.InterestRelationAccessDeniedException;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Application unit]: 관심사 관계 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteInterestRelationServiceTest {

	@InjectMocks
	private DeleteInterestRelationService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private InterestRelationService interestRelationService;

	@DisplayName("관심사 관계를 삭제한다.")
	@Test
	void delete_interest_relation() {
		Interest parentInterest = InterestFixture.create();
		Interest childInterest = InterestFixture.create();
		InterestRelation interestRelation = InterestRelationFixture.create(parentInterest, childInterest);
		doReturn(parentInterest).when(interestService).find(parentInterest.getId());
		doReturn(childInterest).when(interestService).find(childInterest.getId());
		doReturn(interestRelation).when(interestRelationService).find(any());

		sut.delete(interestRelation.getRegistrantId(), interestRelation.getId());

		verify(interestRelationService, times(1)).delete(any(), any(), any());
	}

	@DisplayName("권한 없는 접근자는 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		InterestRelation interestRelation = mock(InterestRelation.class);
		doReturn(interestRelation).when(interestRelationService).find(any());
		doThrow(InterestRelationAccessDeniedException.class).when(interestRelation).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), UUID.randomUUID())).isInstanceOf(InterestRelationAccessDeniedException.class);
	}
}
