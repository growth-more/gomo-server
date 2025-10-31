package com.gomo.app.core.interest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.application.port.dto.InterestDto;
import com.gomo.app.core.interest.application.port.dto.InterestNetworkDto;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;
import com.gomo.app.core.interest.fixture.MajorInterestFixture;

@DisplayName("[Application unit]: 관심사 네트워크 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class InterestNetworkServiceTest {

	@InjectMocks
	private InterestNetworkService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private InterestRelationService interestRelationService;

	@DisplayName("관심사 네트워크를 조회한다.")
	@Test
	void read_interest_network() {
		Interest expected1 = InterestFixture.create();
		Interest expected2 = InterestFixture.create();
		MajorInterest majorInterest = MajorInterestFixture.create(expected1.getRegistrantId(), expected1.getId());
		InterestDto dto1 = InterestDto.of(expected1, majorInterest.getId());
		InterestDto dto2 = InterestDto.of(expected2, null);
		doReturn(List.of(dto1, dto2)).when(interestService).readAll(any());

		InterestRelation relation = InterestRelationFixture.create();
		doReturn(List.of(relation)).when(interestRelationService).readAllByRegistrantId(any());

		InterestNetworkDto actual = sut.read(relation.getRegistrantId());

		assertThat(actual.interestDtos()).hasSize(2);
		assertThat(actual.relationDtos()).hasSize(1);
	}
}
