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

import com.gomo.app.core.interest.application.CreateInterestRelationUseCase;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.service.InterestRelationService;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Application unit]: 관심사 관계 등록 테스트")
@ExtendWith(MockitoExtension.class)
public class CreateInterestRelationUseCaseTest {

	@InjectMocks
	private CreateInterestRelationUseCase sut;

	@Mock
	private InterestRelationService interestRelationService;

	@DisplayName("관심사 관계를 등록한다.")
	@Test
	void create_interest_relation() {
		InterestRelation interestRelation = InterestRelationFixture.create();
		doReturn(interestRelation).when(interestRelationService).create(any(), any(), any());

		UUID actual = sut.create(interestRelation.registrantId(), interestRelation.getParentInterestId().getId(), interestRelation.getChildInterestId().getId());

		assertThat(actual).isEqualTo(interestRelation.id());
	}
}
