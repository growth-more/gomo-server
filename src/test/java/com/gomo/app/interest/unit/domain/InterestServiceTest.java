package com.gomo.app.interest.unit.domain;

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

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.domain.service.InterestService;
import com.gomo.app.interest.exception.InterestNotFoundException;
import com.gomo.app.interest.exception.code.InterestErrorCode;
import com.gomo.app.interest.fixture.InterestFixture;

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
		Interest interest = InterestFixture.create();
		doReturn(Optional.of(interest)).when(interestRepository).findById(any());

		Interest actual = sut.find(interest.getId());

		assertThat(actual.getId()).isEqualTo(interest.getId());
	}

	@DisplayName("존재하지 않는 관심사를 조회한다.")
	@Test
	void read_nonexistent_interest() {
		doReturn(Optional.empty()).when(interestRepository).findById(any());

		assertThatThrownBy(() -> sut.find(InterestId.of(UUID.randomUUID())))
			.isInstanceOf(InterestNotFoundException.class)
			.hasMessageContaining(InterestErrorCode.NOT_FOUND.getMessage());
	}
}
