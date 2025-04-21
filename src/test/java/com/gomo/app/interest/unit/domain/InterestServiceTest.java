package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestQuota;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.InterestService;

@ExtendWith(MockitoExtension.class)
@DisplayName("[Domain unit]: 관심사 생성 테스트")
public class InterestServiceTest {

	@InjectMocks

	InterestService sut;

	@Mock
	InterestRepository interestRepository;

	@DisplayName("관심사를 생성한다.")
	@Test
	void create_interest() {
		Interest interest = InterestFixture.interest();
		doReturn(99L).when(interestRepository).countAllByRegistrantId(any(RegistrantId.class));
		doReturn(interest).when(interestRepository).save(any());

		Interest actual = sut.create(InterestFixture.interest(), InterestQuota.BASIC);

		assertThat(actual.getId()).isEqualTo(interest.getId());
	}
}
