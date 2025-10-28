package com.gomo.app.core.interest.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.exception.InterestNotFoundException;
import com.gomo.app.core.interest.domain.exception.code.InterestErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.fixture.InterestFixture;

@DisplayName("[Domain unit]: 관심사 조회 테스트")
@ExtendWith(MockitoExtension.class)
public class InterestServiceTest {

	@InjectMocks
	private InterestService sut;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 조회한다.")
	@Test
	void read_interest() {
		Interest interest = InterestFixture.create();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());

		Interest actual = sut.find(interest.getId());

		assertThat(actual.getId()).isEqualTo(interest.getId());
	}

	@DisplayName("존재하지 않는 관심사를 조회한다.")
	@Test
	void read_nonexistent_interest() {
		doReturn(Optional.empty()).when(interestRepository).findById(any());

		assertThatThrownBy(() -> sut.find(UUID.randomUUID()))
			.isInstanceOf(InterestNotFoundException.class)
			.hasMessageContaining(InterestErrorCode.NOT_FOUND.getMessage());
	}
}
