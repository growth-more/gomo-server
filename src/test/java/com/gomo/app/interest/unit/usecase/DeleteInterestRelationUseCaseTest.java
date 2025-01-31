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

import com.gomo.app.interest.application.DeleteInterestRelationUseCase;
import com.gomo.app.interest.common.fixture.InterestRelationFixture;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.exception.InterestRelationAccessDeniedException;

@DisplayName("[Application unit]: 관심사 관계 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteInterestRelationUseCaseTest {

	@InjectMocks
	private DeleteInterestRelationUseCase sut;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@DisplayName("관심사 관계를 삭제한다.")
	@Test
	void delete_interest_relation() {
		InterestRelation interestRelation = InterestRelationFixture.relation();
		doReturn(Optional.of(interestRelation)).when(interestRelationRepository).findById(any(InterestRelationId.class));

		sut.delete(interestRelation.getRegistrantId().getId(), interestRelation.getId());

		verify(interestRelationRepository, times(1)).delete(any(InterestRelation.class));
	}

	@DisplayName("권한 없는 접근자는 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		InterestRelation interestRelation = mock(InterestRelation.class);
		doReturn(Optional.of(interestRelation)).when(interestRelationRepository).findById(any(InterestRelationId.class));
		doThrow(InterestRelationAccessDeniedException.class).when(interestRelation).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), InterestRelationId.of(UUID.randomUUID())))
			.isInstanceOf(InterestRelationAccessDeniedException.class);
	}
}
