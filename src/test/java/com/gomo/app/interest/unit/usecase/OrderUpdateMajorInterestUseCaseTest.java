package com.gomo.app.interest.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.interest.application.OrderUpdateMajorInterestUseCase;
import com.gomo.app.interest.common.fixture.MajorInterestFixture;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;

@DisplayName("[Application unit]: 주요 관심사 정렬 순서 변경 테스트")
@ExtendWith(MockitoExtension.class)
public class OrderUpdateMajorInterestUseCaseTest {

	@InjectMocks
	private OrderUpdateMajorInterestUseCase sut;

	@Mock
	private MajorInterestRepository majorInterestRepository;

	@DisplayName("주요 관심사 정렬 순서를 변경한다.")
	@Test
	void update_interest_display_order() {
		OrderUpdateMajorInterestRequest request = OrderUpdateMajorInterestRequest.of(List.of(3, 2, 1));
		List<MajorInterest> majorInterests = List.of(
			MajorInterestFixture.majorInterest(1),
			MajorInterestFixture.majorInterest(2),
			MajorInterestFixture.majorInterest(3)
		);
		doReturn(majorInterests).when(majorInterestRepository).findAllByRegistrantIdOrderByDisplayOrder(any(RegistrantId.class));

		sut.update(UUID.randomUUID(), request);

		verify(majorInterestRepository, times(1)).findAllByRegistrantIdOrderByDisplayOrder(any(RegistrantId.class));
	}
}
