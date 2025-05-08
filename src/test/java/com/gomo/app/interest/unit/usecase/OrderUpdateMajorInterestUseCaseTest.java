package com.gomo.app.interest.unit.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.displayorder.OrderChanger;
import com.gomo.app.interest.application.OrderUpdateMajorInterestUseCase;
import com.gomo.app.interest.fixture.MajorInterestFixture;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.interest.presentation.request.UpdateOrderRequest;

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
		doReturn(getMajorInterests()).when(majorInterestRepository).findAllByRegistrantIdOrderByDisplayOrder(any());

		try (MockedStatic<OrderChanger> mockedOrderChanger = mockStatic(OrderChanger.class)) {
			sut.update(UUID.randomUUID(), getRequest());

			verify(majorInterestRepository, times(1)).findAllByRegistrantIdOrderByDisplayOrder(any());
			mockedOrderChanger.verify(() -> OrderChanger.change(any(), any()), times(1));
		}
	}

	private @NotNull OrderUpdateMajorInterestRequest getRequest() {
		return OrderUpdateMajorInterestRequest.of(
			List.of(
				UpdateOrderRequest.of(UUID.randomUUID(), 1),
				UpdateOrderRequest.of(UUID.randomUUID(), 2),
				UpdateOrderRequest.of(UUID.randomUUID(), 3)
			)
		);
	}

	private @NotNull List<MajorInterest> getMajorInterests() {
		return List.of(
			MajorInterestFixture.majorInterest(),
			MajorInterestFixture.majorInterest(),
			MajorInterestFixture.majorInterest()
		);
	}
}
