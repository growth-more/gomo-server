package com.gomo.app.core.interest.application.usecase;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.application.UpdateInterestUseCase;
import com.gomo.app.core.interest.application.port.command.UpdateInterestCommand;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.fixture.InterestFixture;

@DisplayName("[Application unit]: 관심사 수정 테스트")
@ExtendWith(MockitoExtension.class)
public class UpdateInterestUseCaseTest {

	@InjectMocks
	private UpdateInterestUseCase sut;

	@Mock
	private InterestService interestService;

	@DisplayName("관심사를 수정한다.")
	@Test
	void update_interest() {
		Interest interest = InterestFixture.create();
		doReturn(interest).when(interestService).find(any(InterestId.class));

		sut.update(UpdateInterestCommand.of(interest.registrantUuid(), interest.uuid(), "name", "#FF0000"));

		verify(interestService, times(1)).find(any(InterestId.class));
	}

	@DisplayName("관심사를 수정하기 전, 권한 검사를 한다.")
	@Test
	void update_interest_by_unauthorized_accessor() {
		Interest interest = Mockito.mock(Interest.class);
		doReturn(interest).when(interestService).find(any(InterestId.class));

		sut.update(UpdateInterestCommand.of(UUID.randomUUID(), UUID.randomUUID(), "name", "#FF0000"));

		verify(interest, times(1)).validateAuthority(any(UUID.class));
	}
}
