package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.image.ImageService;
import com.gomo.app.interest.application.DeleteInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.common.fixture.InterestRelationFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.domain.service.InterestRelationService;
import com.gomo.app.interest.exception.InterestAccessDeniedException;

@DisplayName("[Application unit]: 관심사 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteInterestUseCaseTest {

	@InjectMocks
	private DeleteInterestUseCase sut;

	@Mock
	private ImageService imageService;

	@Mock
	private InterestRelationService interestRelationService;

	@Mock
	private InterestRepository interestRepository;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@DisplayName("관심사를 삭제한다.")
	@Test
	void delete_interest() {
		Interest interest = InterestFixture.interest();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());
		doReturn(List.of(InterestRelationFixture.relation())).when(interestRelationRepository).findByInterestId(any());

		sut.delete(interest.getRegistrantId().getId(), interest.getId());

		verify(interestRepository, times(1)).delete(any());
	}

	@DisplayName("권한 없는 접근자는 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		Interest interest = mock(Interest.class);
		doReturn(Optional.of(interest)).when(interestRepository).findById(any(InterestId.class));
		doThrow(InterestAccessDeniedException.class).when(interest).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), InterestId.of(UUID.randomUUID())))
			.isInstanceOf(InterestAccessDeniedException.class);
	}

	@DisplayName("관심사의 로고가 기본 로고라면 이미지는 삭제되지 않는다.")
	@Test
	void does_not_delete_interest_logo() {
		Interest interest = InterestFixture.defaultLogo();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());
		doReturn(List.of(InterestRelationFixture.relation())).when(interestRelationRepository).findByInterestId(any());

		sut.delete(interest.getRegistrantId().getId(), interest.getId());

		verify(imageService, times(0)).deleteImage(any());
	}

	@DisplayName("관심사의 로고가 사용자가 업로드한 로고라면 이미지는 삭제된다.")
	@Test
	void delete_interest_logo() {
		Interest interest = InterestFixture.interest();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());
		doReturn(List.of(InterestRelationFixture.relation())).when(interestRelationRepository).findByInterestId(any());

		sut.delete(interest.getRegistrantId().getId(), interest.getId());

		verify(imageService, times(1)).deleteImage(any());
	}

	@DisplayName("관심사를 삭제할 때, 주요 관심사도 함께 삭제된다.")
	@Test
	void delete_major_interest() {
		Interest interest = InterestFixture.interest();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());
		doReturn(List.of(InterestRelationFixture.relation())).when(interestRelationRepository).findByInterestId(any());

		sut.delete(interest.getRegistrantId().getId(), interest.getId());

		verify(majorInterestRepository, times(1)).deleteByInterestId(any());
	}

	@DisplayName("관심사를 삭제할 때, 관심사와 연결된 모든 관계선도 함께 삭제된다.")
	@Test
	void delete_major_interest_relation() {
		Interest interest = InterestFixture.interest();
		List<InterestRelation> interestRelations = List.of(InterestRelationFixture.relation());
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());
		doReturn(interestRelations).when(interestRelationRepository).findByInterestId(any());

		sut.delete(interest.getRegistrantId().getId(), interest.getId());

		verify(interestRelationService, times(interestRelations.size())).delete(any(), any());
	}

	@DisplayName("관심사를 삭제할 때, 관심사와 연결된 관계선이 없다면 관계선은 삭제하지 않는다.")
	@Test
	void does_not_delete_major_interest_relation() {
		Interest interest = InterestFixture.interest();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());
		doReturn(List.of()).when(interestRelationRepository).findByInterestId(any());

		sut.delete(interest.getRegistrantId().getId(), interest.getId());

		verify(interestRelationService, times(0)).delete(any(), any());
	}
}
