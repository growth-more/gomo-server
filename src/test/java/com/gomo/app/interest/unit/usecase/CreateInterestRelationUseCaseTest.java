package com.gomo.app.interest.unit.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.CreateInterestRelationUseCase;
import com.gomo.app.interest.common.fixture.InterestRelationFixture;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;
import com.gomo.app.interest.presentation.response.CreateInterestRelationResponse;

@DisplayName("[Application unit]: 관심사 관계 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateInterestRelationUseCaseTest {

	@InjectMocks
	private CreateInterestRelationUseCase sut;

	@Mock
	private InterestRelationRepository interestRelationRepository;

	@DisplayName("관심사 관계를 등록한다.")
	@Test
	void create_interest_relation() {
		InterestRelation interestRelation = InterestRelationFixture.relation();
		CreateInterestRelationResponse expected = CreateInterestRelationResponse.of(interestRelation.getId());
		doReturn(interestRelation).when(interestRelationRepository).save(any(InterestRelation.class));

		CreateInterestRelationResponse actual = sut.create(
			interestRelation.getRegistrantId(),
			CreateInterestRelationRequest.of(
				interestRelation.getRegistrantId().getId(),
				interestRelation.getParentInterestId().getId(),
				interestRelation.getChildInterestId().getId()
			)
		);

		assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
	}
}
