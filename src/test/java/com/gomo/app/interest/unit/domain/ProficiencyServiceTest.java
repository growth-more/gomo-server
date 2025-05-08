package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.interest.fixture.InterestFixture;
import com.gomo.app.interest.fixture.InterestRelationFixture;
import com.gomo.app.interest.fixture.ScoreThresholdPolicyFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.repository.ScoreThresholdPolicyCache;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.domain.service.ProficiencyService;

@DisplayName("[Domain unit]: 숙련도 향상 테스트")
public class ProficiencyServiceTest extends IntegrationTestBase {

	@InjectMocks
	private ProficiencyService sut;

	@Mock
	private ScoreThresholdPolicyCache scoreThresholdPolicyCache;

	@Mock
	private InterestService interestService;

	@Mock
	private InterestRepository interestRepository;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	private RegistrantId registrantId;

	@BeforeEach
	public void setUp() {
		registrantId = RegistrantId.of(UUID.randomUUID());

		doReturn(ScoreThresholdPolicyFixture.scoreThresholdPolicyCache()).when(scoreThresholdPolicyCache).getScoreThresholdPerLevel();
		doReturn(ScoreThresholdPolicyFixture.totalScoreForLevelCache()).when(scoreThresholdPolicyCache).getTotalScoreForLevel();
	}

	@DisplayName("하위 관심사의 숙련도가 향상된다면, 모든 상위 관심사의 숙련도도 동일한 수치만큼 향상된다.")
	@Test
	void enhance_proficiency() {
		Interest depth1 = InterestFixture.create(registrantId, "depth1", 15);
		Interest depth2 = InterestFixture.create(registrantId, "depth2", 10);
		Interest depth3 = InterestFixture.create(registrantId, "depth3", 5);
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);

		doReturn(List.of(depth1, depth2, depth3)).when(interestRepository).findAllById(any());
		doReturn(List.of(depth1ToDepth2, depth2ToDepth3)).when(interestRelationRepository).findAll();
		doReturn(depth3).when(interestService).find(any());

		sut.adjust(depth3.getId(), 20);

		assertThat(depth1.getProficiency().score()).isEqualTo(35);
		assertThat(depth2.getProficiency().score()).isEqualTo(30);
		assertThat(depth3.getProficiency().score()).isEqualTo(25);
	}

	@DisplayName("하위 관심사의 숙련도가 감소하면, 모든 상위 관심사의 숙련도도 동일한 수치만큼 감소한다.")
	@Test
	void reduce_proficiency() {
		Interest depth1 = InterestFixture.create(registrantId, "depth1", 15);
		Interest depth2 = InterestFixture.create(registrantId, "depth2", 10);
		Interest depth3 = InterestFixture.create(registrantId, "depth3", 5);
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);

		doReturn(List.of(depth1, depth2, depth3)).when(interestRepository).findAllById(any());
		doReturn(List.of(depth1ToDepth2, depth2ToDepth3)).when(interestRelationRepository).findAll();
		doReturn(depth3).when(interestService).find(any());

		sut.adjust(depth3.getId(), -5);

		assertThat(depth1.getProficiency().score()).isEqualTo(10);
		assertThat(depth2.getProficiency().score()).isEqualTo(5);
		assertThat(depth3.getProficiency().score()).isEqualTo(0);
	}
}
