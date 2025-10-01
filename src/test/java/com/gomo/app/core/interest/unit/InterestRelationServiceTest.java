package com.gomo.app.core.interest.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.model.ChildInterestId;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.InterestRelationId;
import com.gomo.app.core.interest.domain.model.ParentInterestId;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.domain.service.ProficiencyService;
import com.gomo.app.core.interest.exception.InterestRelationCycleException;
import com.gomo.app.core.interest.exception.InterestRelationDuplicatedException;
import com.gomo.app.core.interest.exception.InterestRelationNotFoundException;
import com.gomo.app.core.interest.exception.code.InterestRelationErrorCode;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Domain integration]: 관심사 관계선 생성 테스트")
@ExtendWith(MockitoExtension.class)
public class InterestRelationServiceTest {

	@InjectMocks
	private InterestRelationService sut;

	@Mock
	private ProficiencyService proficiencyService;

	@Mock
	private InterestService interestService;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@DisplayName("관심사 관계선을 생성하면 상위 관심사의 총 점수가 하위 관심사의 총 점수 만큼 증가한다.")
	@Test
	void create_relation() {
		RegistrantId registrantId = RegistrantId.of(UUID.randomUUID());
		Interest depth1 = InterestFixture.create(registrantId, "depth1");
		Interest depth2 = InterestFixture.create(registrantId, "depth2");
		ParentInterestId parentInterestId = ParentInterestId.of(depth1.getId());
		ChildInterestId childInterestId = ChildInterestId.of(depth2.getId());

		doReturn(false).when(interestRelationRepository).existsRelationFor(any(), any());
		doReturn(new ArrayList<>()).when(interestRelationRepository).findAllByRegistrantId(any());
		doReturn(InterestFixture.create()).when(interestService).find(any());

		sut.create(registrantId, parentInterestId, childInterestId);

		verify(proficiencyService, times(1)).adjust(any(), eq(0));
		verify(interestRelationRepository, times(1)).save(any());
	}

	@DisplayName("관심사 관계선은 중복 생성할 수 없다.")
	@Test
	void create_duplicated_relation() {
		RegistrantId registrantId = RegistrantId.of(UUID.randomUUID());
		ParentInterestId parentInterestId = ParentInterestId.of(InterestId.of(UUID.randomUUID()));
		ChildInterestId childInterestId = ChildInterestId.of(InterestId.of(UUID.randomUUID()));

		doReturn(true).when(interestRelationRepository).existsRelationFor(any(), any());

		assertThatThrownBy(() -> sut.create(registrantId, parentInterestId, childInterestId))
			.isInstanceOf(InterestRelationDuplicatedException.class)
			.hasMessageContaining(InterestRelationErrorCode.DUPLICATED.getMessage());
	}

	@DisplayName("같은 방향으로 순환이 발생하지 않는 관심사 관계선을 추가한다.")
	@Test
	void create_no_cyclic_relation() {
		// given: 1 -> 2, 2 -> 3, when: 1 -> 3
		RegistrantId registrantId = RegistrantId.of(UUID.randomUUID());
		Interest depth1 = InterestFixture.create(registrantId, "depth1");
		Interest depth2 = InterestFixture.create(registrantId, "depth2");
		Interest depth3 = InterestFixture.create(registrantId, "depth3");
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
		List<InterestRelation> relations = new ArrayList<>();
		relations.add(depth1ToDepth2);
		relations.add(depth2ToDepth3);

		doReturn(false).when(interestRelationRepository).existsRelationFor(any(), any());
		doReturn(relations).when(interestRelationRepository).findAllByRegistrantId(any());
		doReturn(depth3).when(interestService).find(any());

		assertThatCode(() -> sut.create(registrantId, ParentInterestId.of(depth1.getId()), ChildInterestId.of(depth3.getId())))
			.doesNotThrowAnyException();
	}

	@DisplayName("같은 방향으로 순환이 발생하는 관심사 관계선은 생성할 수 없다.")
	@Test
	void create_cyclic_relation() {
		// given: 1 -> 2, 2 -> 3, when: 3 -> 1
		RegistrantId registrantId = RegistrantId.of(UUID.randomUUID());
		Interest depth1 = InterestFixture.create(registrantId, "depth1");
		Interest depth2 = InterestFixture.create(registrantId, "depth2");
		Interest depth3 = InterestFixture.create(registrantId, "depth3");
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
		List<InterestRelation> relations = new ArrayList<>();
		relations.add(depth1ToDepth2);
		relations.add(depth2ToDepth3);

		doReturn(false).when(interestRelationRepository).existsRelationFor(any(), any());
		doReturn(relations).when(interestRelationRepository).findAllByRegistrantId(any());

		assertThatThrownBy(() -> sut.create(registrantId, ParentInterestId.of(depth3.getId()), ChildInterestId.of(depth1.getId())))
			.isInstanceOf(InterestRelationCycleException.class)
			.hasMessageContaining(InterestRelationErrorCode.UNEXPECTED_CYCLE.getMessage());
	}

	@DisplayName("관심사 관계선을 조회한다.")
	@Test
	void find_relation() {
		InterestRelation interestRelation = InterestRelationFixture.create();
		doReturn(Optional.of(interestRelation)).when(interestRelationRepository).findById(any());

		InterestRelation actual = sut.find(interestRelation.getId());

		assertThat(actual.getId()).isEqualTo(interestRelation.getId());
	}

	@DisplayName("존재하지 않는 관심사 관계선을 조회한다.")
	@Test
	void find_nonexistent_relation() {
		doReturn(Optional.empty()).when(interestRelationRepository).findById(any());

		assertThatThrownBy(() -> sut.find(InterestRelationId.of(UUID.randomUUID())))
			.isInstanceOf(InterestRelationNotFoundException.class)
			.hasMessageContaining(InterestRelationErrorCode.NOT_FOUND.getMessage());
	}

	@DisplayName("관심사 관계선 목록을 조회한다.")
	@Test
	void find_all_relation_by_interest() {
		doReturn(List.of(InterestRelationFixture.create(), InterestRelationFixture.create())).when(interestRelationRepository).findAllByInterestId(any());

		List<InterestRelation> actual = sut.findAllByInterestId(UUID.randomUUID());

		assertThat(actual).hasSize(2);
	}

	@DisplayName("관심사 관계선을 삭제하면 상위 관심사의 총 점수가 하위 관심사의 총 점수 만큼 감소한다.")
	@Test
	void delete_relation() {
		doReturn(InterestFixture.create()).when(interestService).find(any());

		sut.delete(InterestRelationFixture.create());

		verify(proficiencyService, times(1)).adjust(any(), eq(0));
		verify(interestRelationRepository, times(1)).delete(any());
	}
}
