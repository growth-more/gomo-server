package com.gomo.app.core.interest.application.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;
import com.gomo.app.support.image.port.DeleteImagePortIn;

@DisplayName("[Application unit]: 관심사 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteInterestUseCaseTest {

	@InjectMocks
	private DeleteInterestUseCase sut;

	@Mock
	private InterestService interestService;

	@Mock
	private DeleteImagePortIn deleteImagePortIn;

	@Mock
	private InterestRelationService interestRelationService;

	@Mock
	private InterestRepository interestRepository;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@DisplayName("관심사를 삭제한다.")
	@Test
	void delete_interest() {
		Interest interest = InterestFixture.create();
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(List.of(InterestRelationFixture.create())).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(interest.getRegistrantId().getId(), interest.id());

		verify(interestRepository, times(1)).delete(any());
	}

	@DisplayName("관심사를 삭제하기 전, 권한 검사를 한다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		Interest interest = Mockito.mock(Interest.class);
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(Logo.of("logoFile")).when(interest).getLogo();
		doReturn(List.of(InterestRelationFixture.create())).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(UUID.randomUUID(), UUID.randomUUID());

		verify(interest, times(1)).validateAuthority(any(UUID.class));
	}

	@DisplayName("관심사의 로고가 기본 로고라면 이미지는 삭제되지 않는다.")
	@Test
	void does_not_delete_interest_logo() {
		Interest interest = InterestFixture.create(Logo.of(null));
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(List.of(InterestRelationFixture.create())).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(interest.getRegistrantId().getId(), interest.id());

		verify(deleteImagePortIn, times(0)).delete(any());
	}

	@DisplayName("관심사의 로고가 사용자가 업로드한 로고라면 이미지는 삭제된다.")
	@Test
	void delete_interest_logo() {
		Interest interest = InterestFixture.create();
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(List.of(InterestRelationFixture.create())).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(interest.getRegistrantId().getId(), interest.id());

		verify(deleteImagePortIn, times(1)).delete(any());
	}

	@DisplayName("관심사를 삭제할 때, 주요 관심사도 함께 삭제된다.")
	@Test
	void delete_major_interest() {
		Interest interest = InterestFixture.create();
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(List.of(InterestRelationFixture.create())).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(interest.getRegistrantId().getId(), interest.id());

		verify(majorInterestRepository, times(1)).deleteByInterestId(any());
	}

	@DisplayName("관심사를 삭제할 때, 관심사와 연결된 모든 관계선도 함께 삭제된다.")
	@Test
	void delete_major_interest_relation() {
		Interest interest = InterestFixture.create();
		List<InterestRelation> interestRelations = List.of(InterestRelationFixture.create());
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(interestRelations).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(interest.getRegistrantId().getId(), interest.id());

		verify(interestRelationService, times(interestRelations.size())).delete(any(), any(), any());
	}

	@DisplayName("관심사를 삭제할 때, 관심사와 연결된 관계선이 없다면 관계선은 삭제하지 않는다.")
	@Test
	void does_not_delete_major_interest_relation() {
		Interest interest = InterestFixture.create();
		doReturn(interest).when(interestService).find(any(InterestId.class));
		doReturn(List.of()).when(interestRelationService).findAllByInterestId(any(UUID.class));

		sut.delete(interest.getRegistrantId().getId(), interest.id());

		verify(interestRelationService, times(0)).delete(any(), any(), any());
	}
}
