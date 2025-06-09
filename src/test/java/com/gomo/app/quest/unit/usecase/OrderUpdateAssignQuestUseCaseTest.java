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
import com.gomo.app.quest.application.OrderUpdateAssignQuestUseCase;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.fixture.AssignQuestFixture;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

@DisplayName("[Application unit]: 참여 중인 퀘스트 정렬 순서 변경 테스트")
@ExtendWith(MockitoExtension.class)
public class OrderUpdateAssignQuestUseCaseTest {

	@InjectMocks
	private OrderUpdateAssignQuestUseCase sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@DisplayName("참여 중인 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_participating_quest_display_order() {
		OrderUpdateAssignQuestRequest request = getRequest();
		doReturn(getParticipatingQuests()).when(assignQuestRepository).findParticipatingQuestByQuestTypeWithoutCompleted(any(), any(), any(), any());

		try (MockedStatic<OrderChanger> mockedOrderChanger = mockStatic(OrderChanger.class)) {
			sut.update(UUID.randomUUID(), request);

			verify(assignQuestRepository, times(1)).findParticipatingQuestByQuestTypeWithoutCompleted(any(), any(), any(), any());
			mockedOrderChanger.verify(() -> OrderChanger.change(any(), any()), times(1));
		}
	}

	private @NotNull OrderUpdateAssignQuestRequest getRequest() {
		return OrderUpdateAssignQuestRequest.of(
			QuestType.DAILY,
			List.of(
				UpdateOrderRequest.of(UUID.randomUUID(), 1),
				UpdateOrderRequest.of(UUID.randomUUID(), 2),
				UpdateOrderRequest.of(UUID.randomUUID(), 3)
			)
		);
	}

	private @NotNull List<AssignQuest> getParticipatingQuests() {
		return List.of(
			AssignQuestFixture.assignQuest(1),
			AssignQuestFixture.assignQuest(2),
			AssignQuestFixture.assignQuest(3)
		);
	}
}
