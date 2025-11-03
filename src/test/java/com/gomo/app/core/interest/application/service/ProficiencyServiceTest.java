package com.gomo.app.core.interest.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.service.InterestNetworkBuilder;
import com.gomo.app.core.interest.domain.service.ProficiencyCalculator;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Application unit]: 숙련도 조정 테스트")
@ExtendWith(MockitoExtension.class)
class ProficiencyServiceTest {

	@InjectMocks
	private ProficiencyService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private InterestNetworkBuilder interestNetworkBuilder;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@Mock
	private ProficiencyCalculator proficiencyCalculator;

	@DisplayName("하위 관심사의 숙련도가 조정되면, 모든 상위 관심사의 숙련도도 동일한 수치만큼 조정된다.")
	@Test
	void propagate_proficiency() {
		Interest depth1 = spy(InterestFixture.create());
		Interest depth2 = spy(InterestFixture.create());
		Interest depth3 = spy(InterestFixture.create());
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
		Map<UUID, Set<Interest>> map = Map.of(
			depth2.getId(), Set.of(depth1),
			depth3.getId(), Set.of(depth2)
		);
		doReturn(depth3).when(interestService).readById(any());
		doReturn(List.of(depth1ToDepth2, depth2ToDepth3)).when(interestRelationRepository).findAllByRegistrantId(any());
		doReturn(map).when(interestNetworkBuilder).buildParentInterestByChildId(any());

		sut.propagate(depth3.getId(), 20);

		verify(depth1, times(1)).adjustProficiency(eq(20), any());
		verify(depth2, times(1)).adjustProficiency(eq(20), any());
		verify(depth3, times(1)).adjustProficiency(eq(20), any());
	}
}
