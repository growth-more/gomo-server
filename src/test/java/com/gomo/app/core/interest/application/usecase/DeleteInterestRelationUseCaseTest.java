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

import com.gomo.app.core.interest.application.DeleteInterestRelationUseCase;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.InterestRelationId;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.exception.InterestRelationAccessDeniedException;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Application unit]: 관심사 관계 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteInterestRelationUseCaseTest {

	@InjectMocks
	private DeleteInterestRelationUseCase sut;

	@Mock
	private InterestRelationService interestRelationService;

	@DisplayName("관심사 관계를 삭제한다.")
	@Test
	void delete_interest_relation() {
		InterestRelation interestRelation = InterestRelationFixture.create();
		doReturn(interestRelation).when(interestRelationService).find(any(InterestRelationId.class));

		sut.delete(interestRelation.registrantId(), interestRelation.id());

		verify(interestRelationService, times(1)).delete(any(InterestRelation.class));
	}

	@DisplayName("권한 없는 접근자는 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		InterestRelation interestRelation = mock(InterestRelation.class);
		doReturn(interestRelation).when(interestRelationService).find(any(InterestRelationId.class));
		doThrow(InterestRelationAccessDeniedException.class).when(interestRelation).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(InterestRelationAccessDeniedException.class);
	}
}
