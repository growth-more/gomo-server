package com.gomo.app.quest.unit.usecase;

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
import com.gomo.app.interest.presentation.request.UpdateOrderRequest;
import com.gomo.app.quest.application.OrderUpdateRepeatQuestUseCase;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.fixture.RepeatQuestFixture;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

@DisplayName("[Application unit]: 반복 퀘스트 정렬 순서 변경 테스트")
@ExtendWith(MockitoExtension.class)
public class OrderUpdateRepeatQuestUseCaseTest {

	@InjectMocks
	private OrderUpdateRepeatQuestUseCase sut;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@DisplayName("반복 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_repeat_quest_display_order() {
		OrderUpdateRepeatQuestRequest request = getRequest();
		doReturn(getRepeatQuests()).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), any());

		try (MockedStatic<OrderChanger> mockedOrderChanger = mockStatic(OrderChanger.class)) {
			sut.update(UUID.randomUUID(), request);

			verify(repeatQuestRepository, times(1)).findRepeatQuestsByQuestType(any(), any());
			mockedOrderChanger.verify(() -> OrderChanger.change(any(), any()), times(1));
		}
	}

	private @NotNull OrderUpdateRepeatQuestRequest getRequest() {
		return OrderUpdateRepeatQuestRequest.of(
			QuestType.DAILY,
			List.of(
				UpdateOrderRequest.of(UUID.randomUUID(), 1),
				UpdateOrderRequest.of(UUID.randomUUID(), 2),
				UpdateOrderRequest.of(UUID.randomUUID(), 3)
			)
		);
	}

	private static @NotNull List<RepeatQuest> getRepeatQuests() {
		return List.of(
			RepeatQuestFixture.repeatQuest(1),
			RepeatQuestFixture.repeatQuest(2),
			RepeatQuestFixture.repeatQuest(3)
		);
	}
}
