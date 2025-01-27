package com.gomo.app.interest.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.UpdateInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

@DisplayName("[Application unit]: 관심사 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateInterestUseCaseTest {

	@InjectMocks
	private UpdateInterestUseCase sut;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 수정한다.")
	@Test
	void update_interest() {
		Interest expected = InterestFixture.interest();
		doReturn(Optional.of(expected)).when(interestRepository).findById(any(InterestId.class));

		sut.update(expected.getRegistrantId().getId(), expected.getId(), UpdateInterestRequest.of("updated interest name"));

		verify(interestRepository, times(1)).findById(any(InterestId.class));
	}
}
