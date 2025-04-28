package com.gomo.app.interest.unit.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.InterestService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 관심사 조회 테스트")
public class InterestServiceTest {

	@InjectMocks

	InterestService sut;

	@Mock
	InterestRepository interestRepository;

	@DisplayName("관심사를 조회한다.")
	@Test
	void read_interest() {
		// Interest interest = InterestFixture.interest();
		// doReturn(99L).when(interestRepository).countAllByRegistrantId(any(RegistrantId.class));
		// doReturn(interest).when(interestRepository).save(any());
		//
		// Interest actual = sut.create(InterestFixture.interest(), InterestQuota.BASIC);
		//
		// assertThat(actual.getId()).isEqualTo(interest.getId());
	}
}
