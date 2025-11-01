package com.gomo.app.core.quest.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.common.displayorder.OrderChanger;
import com.gomo.app.common.displayorder.UpdatedOrderDto;
import com.gomo.app.core.quest.application.port.command.OrderUpdateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.command.UpdateRepeatQuestCommand;
import com.gomo.app.core.quest.application.port.dto.ListRepeatQuestDto;
import com.gomo.app.core.quest.domain.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.RepeatQuestAccessDeniedException;
import com.gomo.app.core.quest.domain.exception.RepeatQuestNotFoundException;
import com.gomo.app.core.quest.domain.exception.code.QuestTypeErrorCode;
import com.gomo.app.core.quest.domain.exception.code.RepeatQuestErrorCode;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;
import com.gomo.app.core.quest.fixture.QuestRewardFixture;
import com.gomo.app.core.quest.fixture.RepeatQuestFixture;

@DisplayName("[Application unit]: 반복 퀘스트 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class RepeatQuestServiceTest {

	@InjectMocks
	private RepeatQuestService sut;

	@Mock
	private RepeatQuestRepository repeatQuestRepository;

	@Mock
	private QuestRewardProvider questRewardProvider;

	@Nested
	@DisplayName("반복 퀘스트 조회 테스트")
	class ReadRepeatQuest {

		@DisplayName("반복 퀘스트 엔티티를 조회한다.")
		@Test
		void find_repeat_quest_entity() {
			RepeatQuest repeatQuest = RepeatQuestFixture.create();
			doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());

			RepeatQuest actual = sut.readById(repeatQuest.getId());

			assertThat(actual).isEqualTo(repeatQuest);
		}

		@DisplayName("존재하지 않는 반복 퀘스트 엔티티를 조회한다.")
		@Test
		void find_repeat_quest_with_nonexistent_id() {
			doReturn(Optional.empty()).when(repeatQuestRepository).findById(any());

			assertThatThrownBy(() -> sut.readById(UUID.randomUUID())).isExactlyInstanceOf(RepeatQuestNotFoundException.class);
		}

		@DisplayName("반복 퀘스트 목록을 조회한다.")
		@Test
		void find_All() {
			List<RepeatQuest> dailyQuests = List.of(RepeatQuestFixture.create(QuestType.DAILY), RepeatQuestFixture.create(QuestType.DAILY));
			List<RepeatQuest> weeklyQuests = List.of(RepeatQuestFixture.create(QuestType.WEEKLY));
			List<RepeatQuest> monthlyQuests = List.of();
			doReturn(QuestRewardFixture.create(1, 10)).when(questRewardProvider).provide(eq(QuestType.DAILY));
			doReturn(QuestRewardFixture.create(2, 20)).when(questRewardProvider).provide(eq(QuestType.WEEKLY));
			doReturn(dailyQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), eq(QuestType.DAILY));
			doReturn(weeklyQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), eq(QuestType.WEEKLY));
			doReturn(monthlyQuests).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), eq(QuestType.MONTHLY));

			ListRepeatQuestDto actual = sut.readAll(UUID.randomUUID());

			assertThat(actual.dailyQuests().size()).isEqualTo(2);
			assertThat(actual.dailyQuests()).extracting("score").containsExactly(1, 1);
			assertThat(actual.dailyQuests()).extracting("point").containsExactly(10, 10);
			assertThat(actual.weeklyQuests().size()).isEqualTo(1);
			assertThat(actual.weeklyQuests()).extracting("score").containsExactly(2);
			assertThat(actual.weeklyQuests()).extracting("point").containsExactly(20);
			assertThat(actual.monthlyQuests().size()).isEqualTo(0);
		}
	}

	@Nested
	@DisplayName("반복 퀘스트 수정 테스트")
	class UpdateRepeatQuest {

		@DisplayName("반복 퀘스트를 수정한다.")
		@Test
		void update_repeat_quest() {
			RepeatQuest repeatQuest = RepeatQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());
			sut.update(getUpdateRepeatQuestCommand(repeatQuest.getQuest().getParticipantId(), QuestType.DAILY.name()));
			assertThat(repeatQuest.getQuest().getSubjectName().toString()).isEqualTo("updated subject name");
		}

		@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 수정할 수 없다.")
		@Test
		void update_repeat_quest_with_not_participant() {
			RepeatQuest repeatQuest = RepeatQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());
			assertThatThrownBy(() -> sut.update(getUpdateRepeatQuestCommand(UUID.randomUUID(), QuestType.WEEKLY.name())))
				.isInstanceOf(RepeatQuestAccessDeniedException.class)
				.hasMessageContaining(RepeatQuestErrorCode.ACCESS_DENIED.getMessage());
		}

		@DisplayName("반복 퀘스트를 다른 퀘스트 타입으로 수정할 수 없다.")
		@Test
		void update_repeat_quest_with_different_type() {
			RepeatQuest repeatQuest = RepeatQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());
			assertThatThrownBy(() -> sut.update(getUpdateRepeatQuestCommand(repeatQuest.getQuest().getParticipantId(), QuestType.WEEKLY.name())))
				.isInstanceOf(QuestTypeConstraintViolationException.class)
				.hasMessageContaining(QuestTypeErrorCode.MISMATCHED.getMessage());
		}

		private static @NotNull UpdateRepeatQuestCommand getUpdateRepeatQuestCommand(UUID participantId, String questType) {
			return UpdateRepeatQuestCommand.of(participantId, UUID.randomUUID(), UUID.randomUUID(), "updated subject name", questType, "updated quest content");
		}
	}

	@Nested
	@DisplayName("반복 퀘스트 전시 순서 변경 테스트")
	class UpdateRepeatQuestOrder {

		@DisplayName("반복 퀘스트의 전시 순서를 변경한다.")
		@Test
		void update_repeat_quest_display_order() {
			doReturn(getRepeatQuests()).when(repeatQuestRepository).findRepeatQuestsByQuestType(any(), any());

			try (MockedStatic<OrderChanger> mockedOrderChanger = mockStatic(OrderChanger.class)) {
				sut.update(
					OrderUpdateRepeatQuestCommand.of(
						UUID.randomUUID(),
						QuestType.DAILY.name(),
						List.of(
							UpdatedOrderDto.of(UUID.randomUUID(), 1),
							UpdatedOrderDto.of(UUID.randomUUID(), 2),
							UpdatedOrderDto.of(UUID.randomUUID(), 3)
						)
					)
				);

				verify(repeatQuestRepository, times(1)).findRepeatQuestsByQuestType(any(), any());
				mockedOrderChanger.verify(() -> OrderChanger.change(any()), times(1));
			}
		}

		private static @NotNull List<RepeatQuest> getRepeatQuests() {
			return List.of(
				RepeatQuestFixture.create(1),
				RepeatQuestFixture.create(2),
				RepeatQuestFixture.create(3)
			);
		}
	}

	@Nested
	@DisplayName("반복 퀘스트 삭제 테스트")
	class DeleteRepeatQuest {

		@DisplayName("반복 퀘스트를 삭제한다.")
		@Test
		void delete_repeat_quest() {
			RepeatQuest repeatQuest = RepeatQuestFixture.create();
			doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());

			sut.delete(repeatQuest.getQuest().getParticipantId(), UUID.randomUUID());

			verify(repeatQuestRepository, times(1)).delete(any());
		}

		@DisplayName("퀘스트 참여자가 아니면 반복 퀘스트를 삭제할 수 없다.")
		@Test
		void delete_repeat_quest_by_not_participant() {
			RepeatQuest repeatQuest = RepeatQuestFixture.create();
			doReturn(Optional.of(repeatQuest)).when(repeatQuestRepository).findById(any());

			assertThatThrownBy(
				() -> sut.delete(UUID.randomUUID(), UUID.randomUUID()))
				.isInstanceOf(RepeatQuestAccessDeniedException.class)
				.hasMessageContaining(RepeatQuestErrorCode.ACCESS_DENIED.getMessage());
		}
	}
}
