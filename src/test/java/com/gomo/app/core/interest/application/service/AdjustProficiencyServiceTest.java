package com.gomo.app.core.interest.application.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.service.InterestService;
import com.gomo.app.core.interest.domain.service.ProficiencyService;
import com.gomo.app.core.interest.fixture.InterestFixture;

@DisplayName("[Application unit]: 숙련도 조정 테스트")
@ExtendWith(MockitoExtension.class)
class AdjustProficiencyServiceTest {

	@InjectMocks
	private AdjustProficiencyService sut;

	@Mock
	private InterestService interestService;

	@Mock
	private ProficiencyService proficiencyService;

	@DisplayName("숙련도를 조정한다.")
	@Test
	void adjust_proficiency() {
		doReturn(InterestFixture.create()).when(interestService).find(any());

		sut.adjust(UUID.randomUUID(), 10);

		verify(proficiencyService, times(1)).adjust(any(), anyInt());
	}
}
