package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.exception.InterestRelationAccessDeniedException;
import com.gomo.app.core.interest.domain.exception.InterestRelationCycleException;
import com.gomo.app.core.interest.domain.exception.InterestRelationDuplicatedException;
import com.gomo.app.core.interest.domain.exception.InterestRelationNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.InterestRelationErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.service.InterestNetworkBuilder;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Application unit]: 관심사 관계 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class InterestRelationServiceTest {

	@InjectMocks
	private InterestRelationService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private ProficiencyService proficiencyService;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@Mock
	private InterestNetworkBuilder interestNetworkBuilder;

	@Captor
	private ArgumentCaptor<List<InterestRelation>> relationsCaptor;

	@DisplayName("관심사 관계를 등록한다.")
	@Test
	void create_interest_relation() {
		Interest parentInterest = InterestFixture.create();
		Interest childInterest = InterestFixture.create();
		InterestRelation interestRelation = InterestRelationFixture.create(parentInterest, childInterest);
		doReturn(false).when(interestRelationRepository).existsRelationFor(any(), any());
		doReturn(new ArrayList<>()).when(interestRelationRepository).findAllByRegistrantId(any());
		doReturn(interestRelation).when(interestRelationRepository).save(any());
		doNothing().when(interestNetworkBuilder).validateAcyclic(anyList(), any(UUID.class));
		doReturn(childInterest).when(interestService).readById(childInterest.getId());

		UUID actual = sut.create(interestRelation.getRegistrantId(), parentInterest.getId(), childInterest.getId());

		assertThat(actual).isEqualTo(interestRelation.getId());
		verify(interestNetworkBuilder, times(1)).validateAcyclic(anyList(), any(UUID.class));
		verify(proficiencyService, times(1)).propagate(any(), eq(0));
	}

	@DisplayName("관심사 관계선은 중복 생성할 수 없다.")
	@Test
	void create_duplicated_relation() {
		UUID registrantId = UUID.randomUUID();

		doReturn(true).when(interestRelationRepository).existsRelationFor(any(), any());

		assertThatThrownBy(() -> sut.create(registrantId, UUID.randomUUID(), UUID.randomUUID()))
			.isInstanceOf(InterestRelationDuplicatedException.class)
			.hasMessageContaining(InterestRelationErrorCode.DUPLICATED.getMessage());
		verify(interestNetworkBuilder, never()).validateAcyclic(any(), any());
	}

	@DisplayName("순환이 발생하는 관심사 관계선은 생성할 수 없다.")
	@Test
	void create_cyclic_relation() {
		UUID registrantId = UUID.randomUUID();
		Interest depth1 = InterestFixture.create(registrantId, "depth1");
		Interest depth2 = InterestFixture.create(registrantId, "depth2");
		Interest depth3 = InterestFixture.create(registrantId, "depth3");
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
		List<InterestRelation> relations = new ArrayList<>(List.of(depth1ToDepth2, depth2ToDepth3));
		doReturn(false).when(interestRelationRepository).existsRelationFor(any(), any());
		doReturn(relations).when(interestRelationRepository).findAllByRegistrantId(any());
		doThrow(new InterestRelationCycleException(InterestRelationErrorCode.UNEXPECTED_CYCLE)).when(interestNetworkBuilder)
			.validateAcyclic(anyList(), eq(depth3.getId()));

		assertThatThrownBy(() -> sut.create(registrantId, depth3.getId(), depth1.getId()))
			.isInstanceOf(InterestRelationCycleException.class)
			.hasMessageContaining(InterestRelationErrorCode.UNEXPECTED_CYCLE.getMessage());
	}

	@DisplayName("관심사 관계를 삭제한다.")
	@Test
	void delete_interest_relation() {
		Interest parentInterest = InterestFixture.create();
		Interest childInterest = InterestFixture.create();
		InterestRelation interestRelation = InterestRelationFixture.create(parentInterest, childInterest);
		doReturn(childInterest).when(interestService).readById(childInterest.getId());
		doReturn(Optional.of(interestRelation)).when(interestRelationRepository).findById(any());

		sut.delete(interestRelation.getRegistrantId(), interestRelation.getId());

		verify(proficiencyService, times(1)).propagate(any(), eq(0));
		verify(interestRelationRepository, times(1)).delete(any());
	}

	@DisplayName("권한 없는 접근자는 관심사를 삭제할 수 없다.")
	@Test
	void delete_interest_by_unauthorized_accessor() {
		InterestRelation interestRelation = mock(InterestRelation.class);
		doReturn(Optional.of(interestRelation)).when(interestRelationRepository).findById(any());
		doThrow(InterestRelationAccessDeniedException.class).when(interestRelation).validateAuthority(any(UUID.class));

		assertThatThrownBy(() -> sut.delete(UUID.randomUUID(), UUID.randomUUID())).isInstanceOf(InterestRelationAccessDeniedException.class);
	}

	@DisplayName("관심사 관계선을 조회한다.")
	@Test
	void find_relation() {
		InterestRelation interestRelation = InterestRelationFixture.create();
		doReturn(Optional.of(interestRelation)).when(interestRelationRepository).findById(any());

		InterestRelation actual = sut.readById(interestRelation.getId());

		assertThat(actual.getId()).isEqualTo(interestRelation.getId());
	}

	@DisplayName("존재하지 않는 관심사 관계선을 조회한다.")
	@Test
	void find_nonexistent_relation() {
		doReturn(Optional.empty()).when(interestRelationRepository).findById(any());

		assertThatThrownBy(() -> sut.readById(UUID.randomUUID()))
			.isInstanceOf(InterestRelationNotFoundException.class)
			.hasMessageContaining(InterestRelationErrorCode.NOT_FOUND.getMessage());
	}

	@DisplayName("특정 관심사와 연관된 모든 관계선 목록을 조회한다.")
	@Test
	void find_all_relation_by_interest() {
		doReturn(List.of(InterestRelationFixture.create(), InterestRelationFixture.create())).when(interestRelationRepository).findAllByInterestId(any());

		List<InterestRelation> actual = sut.readAllByInterestId(UUID.randomUUID());

		assertThat(actual).hasSize(2);
	}

	@DisplayName("등록자의 모든 관심사 관계선 목록을 조회한다.")
	@Test
	void find_all_relation_by_registrant() {
		doReturn(List.of(InterestRelationFixture.create(), InterestRelationFixture.create())).when(interestRelationRepository).findAllByRegistrantId(any());

		List<InterestRelation> actual = sut.readAllByRegistrantId(UUID.randomUUID());

		assertThat(actual).hasSize(2);
	}
}
