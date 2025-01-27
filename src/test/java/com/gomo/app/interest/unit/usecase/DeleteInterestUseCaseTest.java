package com.gomo.app.interest.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.DeleteInterestUseCase;
import com.gomo.app.interest.common.fixture.InterestFixture;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.repository.InterestRepository;

@DisplayName("[Application unit]: 관심사 삭제 테스트")
@ExtendWith(MockitoExtension.class)
public class DeleteInterestUseCaseTest {

	@InjectMocks
	private DeleteInterestUseCase sut;

	@Mock
	private InterestRepository interestRepository;

	@DisplayName("관심사를 삭제한다.")
	@Test
	void delete_interest() {
		Interest expected = InterestFixture.interest();
		doReturn(Optional.of(expected)).when(interestRepository).findById(any(InterestId.class));

		sut.delete(expected.getRegistrantId().getId(), expected.getId());

		verify(interestRepository, times(1)).delete(any(Interest.class));
	}
}
