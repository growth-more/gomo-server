package com.gomo.app.core.quest.application.service;

import static com.gomo.app.core.quest.domain.model.quest.QuestType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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
import com.gomo.app.core.quest.application.port.command.ListAssignQuestCommand;
import com.gomo.app.core.quest.application.port.command.OrderUpdateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.command.UpdateAssignQuestCommand;
import com.gomo.app.core.quest.application.port.dto.AssignQuestDto;
import com.gomo.app.core.quest.application.port.dto.ListAssignQuestDetailDto;
import com.gomo.app.core.quest.domain.exception.AssignQuestAccessDeniedException;
import com.gomo.app.core.quest.domain.exception.AssignQuestConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.AssignQuestNotFoundException;
import com.gomo.app.core.quest.domain.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.domain.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.domain.exception.code.QuestTypeErrorCode;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.CompletionProof;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.service.QuestRewardProvider;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.core.quest.fixture.QuestRewardFixture;

@DisplayName("[Application unit]: 할당 퀘스트 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class AssignQuestServiceTest {

	@InjectMocks
	private AssignQuestService sut;

	@Mock
	private AssignQuestRepository assignQuestRepository;

	@Mock
	private QuestRewardProvider questRewardProvider;

	@Nested
	@DisplayName("할당 퀘스트 목록 조회")
	class ReadAssignQuest {

		@DisplayName("완료한 퀘스트의 한달 이력을 조회한다.")
		@Test
		void find_All_completed_quest_for_month() {
			List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
			UUID participantId = UUID.randomUUID();
			LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
			LocalDateTime end = start.plusMonths(1).minusSeconds(1);
			doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndCompletedDateTimeBetween(
				eq(participantId),
				eq(start),
				eq(end)
			);

			List<AssignQuestDto> actual = sut.readAll(ListAssignQuestCommand.of(participantId, true, start, end));

			assertThat(actual.size()).isEqualTo(2);
		}

		@DisplayName("완료한 퀘스트의 하루 이력을 조회한다.")
		@Test
		void find_All_completed_quest_for_day() {
			List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
			UUID participantId = UUID.randomUUID();
			LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
			LocalDateTime end = start.plusDays(1).minusSeconds(1);
			doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndCompletedDateTimeBetween(
				eq(participantId),
				eq(start),
				eq(end)
			);

			List<AssignQuestDto> actual = sut.readAll(ListAssignQuestCommand.of(participantId, true, start, end));

			assertThat(actual.size()).isEqualTo(2);
		}

		@DisplayName("완료하지 못한 퀘스트의 한달 이력을 조회한다.")
		@Test
		void find_All_not_completed_quest_for_month() {
			List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
			UUID participantId = UUID.randomUUID();
			LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
			LocalDateTime end = start.plusMonths(1).minusSeconds(1);
			doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(
				eq(participantId),
				eq(start),
				eq(end)
			);

			List<AssignQuestDto> actual = sut.readAll(ListAssignQuestCommand.of(participantId, false, start, end));

			assertThat(actual.size()).isEqualTo(2);
		}

		@DisplayName("완료하지 못한 퀘스트의 하루 이력을 조회한다.")
		@Test
		void find_All_not_completed_quest_for_day() {
			List<AssignQuest> calendars = List.of(AssignQuestFixture.create(DAILY), AssignQuestFixture.create(WEEKLY));
			UUID participantId = UUID.randomUUID();
			LocalDateTime start = LocalDateTime.of(2025, 2, 1, 0, 0);
			LocalDateTime end = start.plusDays(1).minusSeconds(1);
			doReturn(calendars).when(assignQuestRepository).findByQuestParticipantIdAndStartDateTimeBetweenAndIsCompletedFalse(
				eq(participantId),
				eq(start),
				eq(end)
			);

			List<AssignQuestDto> actual = sut.readAll(ListAssignQuestCommand.of(participantId, false, start, end));

			assertThat(actual.size()).isEqualTo(2);
		}
	}

	@Nested
	@DisplayName("할당 퀘스트 상세 목록 조회")
	class ReadAssignQuestDetail {

		@DisplayName("현재 참여중인 목록을 조회한다.")
		@Test
		void find_All() {
			List<AssignQuest> dailyQuests = List.of(AssignQuestFixture.create(QuestType.DAILY), AssignQuestFixture.create(QuestType.DAILY));
			List<AssignQuest> weeklyQuests = List.of(AssignQuestFixture.create(QuestType.WEEKLY));
			List<AssignQuest> monthlyQuests = List.of();
			doReturn(QuestRewardFixture.create(1, 10)).when(questRewardProvider).provide(eq(QuestType.DAILY));
			doReturn(QuestRewardFixture.create(2, 20)).when(questRewardProvider).provide(eq(QuestType.WEEKLY));
			doReturn(dailyQuests).when(assignQuestRepository).findParticipatingQuestByQuestType(any(), eq(QuestType.DAILY), any(), any());
			doReturn(weeklyQuests).when(assignQuestRepository).findParticipatingQuestByQuestType(any(), eq(QuestType.WEEKLY), any(), any());
			doReturn(monthlyQuests).when(assignQuestRepository).findParticipatingQuestByQuestType(any(), eq(QuestType.MONTHLY), any(), any());

			ListAssignQuestDetailDto actual = sut.readAll(UUID.randomUUID());

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
	@DisplayName("할당 퀘스트 엔티티 조회")
	class ReadAssignQuestEntity {

		@DisplayName("할당 퀘스트를 조회한다.")
		@Test
		void find_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create();
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			AssignQuest actual = sut.readById(assignQuest.getId());

			assertThat(actual).isEqualTo(assignQuest);
		}

		@DisplayName("존재하지 않는 할당 퀘스트를 조회한다.")
		@Test
		void find_nonexistent_assign_quest() {
			doReturn(Optional.empty()).when(assignQuestRepository).findById(any());

			assertThatThrownBy(() -> sut.readById(UUID.randomUUID()))
				.isInstanceOf(AssignQuestNotFoundException.class)
				.hasMessageContaining(AssignQuestErrorCode.NOT_FOUND.getMessage());
		}
	}

	@Nested
	@DisplayName("할당 퀘스트 수정")
	class UpdateAssignQuest {

		@DisplayName("할당 퀘스트를 수정한다.")
		@Test
		void update_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId(), QuestType.DAILY.name()));

			assertThat(assignQuest.getQuest().getSubjectName().toString()).isEqualTo("updated subject name");
		}

		@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 수정할 수 없다.")
		@Test
		void update_assign_quest_with_not_participant() {
			AssignQuest assignQuest = AssignQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(UUID.randomUUID(), QuestType.WEEKLY.name())))
				.isInstanceOf(AssignQuestAccessDeniedException.class)
				.hasMessageContaining("Access denied for the assign quest");
		}

		@DisplayName("할당 퀘스트를 다른 퀘스트 타입으로 수정할 수 없다.")
		@Test
		void update_assign_quest_with_different_type() {
			AssignQuest assignQuest = AssignQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId(), QuestType.WEEKLY.name())))
				.isInstanceOf(QuestTypeConstraintViolationException.class)
				.hasMessageContaining(QuestTypeErrorCode.MISMATCHED.getMessage());
		}

		@DisplayName("이미 확정한 할당 퀘스트는 수정할 수 없다.")
		@Test
		void update_confirmed_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create(true);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId(), QuestType.DAILY.name())))
				.isInstanceOf(AssignQuestConstraintViolationException.class)
				.hasMessageContaining(AssignQuestErrorCode.ALREADY_CONFIRMED.getMessage());
		}

		@DisplayName("이미 완료한 할당 퀘스트는 수정할 수 없다.")
		@Test
		void update_completed_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create(false, true, CompletionProof.createDefault());
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(() -> sut.update(getUpdateAssignQuestCommand(assignQuest.getQuest().getParticipantId(), QuestType.DAILY.name())))
				.isInstanceOf(AssignQuestConstraintViolationException.class)
				.hasMessageContaining(AssignQuestErrorCode.ALREADY_COMPLETED.getMessage());
		}

		private static @NotNull UpdateAssignQuestCommand getUpdateAssignQuestCommand(UUID participantId, String questType) {
			return UpdateAssignQuestCommand.of(participantId, UUID.randomUUID(), UUID.randomUUID(), "updated subject name", questType, "updated quest content");
		}
	}

	@Nested
	@DisplayName("할당 퀘스트 전시 순서 수정")
	class UpdateAssignQuestOrder {

		@DisplayName("참여 중인 퀘스트의 전시 순서를 변경한다.")
		@Test
		void update_participating_quest_display_order() {
			doReturn(getParticipatingQuests()).when(assignQuestRepository).findParticipatingQuestByQuestTypeWithoutCompleted(any(), any(), any(), any());

			try (MockedStatic<OrderChanger> mockedOrderChanger = mockStatic(OrderChanger.class)) {
				sut.update(
					OrderUpdateAssignQuestCommand.of(
						UUID.randomUUID(),
						QuestType.DAILY.name(),
						List.of(
							UpdatedOrderDto.of(UUID.randomUUID(), 1),
							UpdatedOrderDto.of(UUID.randomUUID(), 2),
							UpdatedOrderDto.of(UUID.randomUUID(), 3)
						)
					)
				);

				verify(assignQuestRepository, times(1)).findParticipatingQuestByQuestTypeWithoutCompleted(any(), any(), any(), any());
				mockedOrderChanger.verify(() -> OrderChanger.change(any()), times(1));
			}
		}

		private @NotNull List<AssignQuest> getParticipatingQuests() {
			return List.of(
				AssignQuestFixture.create(1),
				AssignQuestFixture.create(2),
				AssignQuestFixture.create(3)
			);
		}
	}

	@Nested
	@DisplayName("할당 퀘스트 확정")
	class ConfirmAssignQuest {

		@DisplayName("할당 퀘스트를 확정한다.")
		@Test
		void confirm_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			sut.confirm(assignQuest.getQuest().getParticipantId(), UUID.randomUUID());

			assertThat(assignQuest.isConfirmed()).isTrue();
		}

		@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 확정할 수 없다.")
		@Test
		void confirm_assign_quest_with_not_participant() {
			AssignQuest assignQuest = AssignQuestFixture.create(QuestType.DAILY);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(
				() -> sut.confirm(UUID.randomUUID(), UUID.randomUUID()))
				.isInstanceOf(AssignQuestAccessDeniedException.class)
				.hasMessageContaining(AssignQuestErrorCode.ACCESS_DENIED.getMessage());
		}
	}

	@Nested
	@DisplayName("할당 퀘스트 삭제")
	class DeleteAssignQuest {

		@DisplayName("할당 퀘스트를 삭제한다.")
		@Test
		void delete_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create();
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			sut.delete(assignQuest.getQuest().getParticipantId(), UUID.randomUUID());

			verify(assignQuestRepository, times(1)).delete(any(AssignQuest.class));
		}

		@DisplayName("퀘스트 참여자가 아니면 할당 퀘스트를 삭제할 수 없다.")
		@Test
		void delete_assign_quest_by_not_participant() {
			AssignQuest assignQuest = AssignQuestFixture.create();
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(
				() -> sut.delete(UUID.randomUUID(), UUID.randomUUID()))
				.isInstanceOf(AssignQuestAccessDeniedException.class)
				.hasMessageContaining(AssignQuestErrorCode.ACCESS_DENIED.getMessage());
		}

		@DisplayName("이미 확정한 할당 퀘스트는 삭제할 수 없다.")
		@Test
		void delete_confirmed_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create(true);
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(
				() -> sut.delete(assignQuest.getQuest().getParticipantId(), UUID.randomUUID()))
				.isInstanceOf(AssignQuestConstraintViolationException.class)
				.hasMessageContaining(AssignQuestErrorCode.ALREADY_CONFIRMED.getMessage());
		}

		@DisplayName("이미 완료한 할당 퀘스트는 삭제할 수 없다.")
		@Test
		void delete_completed_assign_quest() {
			AssignQuest assignQuest = AssignQuestFixture.create(false, true, CompletionProof.createDefault());
			doReturn(Optional.of(assignQuest)).when(assignQuestRepository).findById(any());

			assertThatThrownBy(
				() -> sut.delete(assignQuest.getQuest().getParticipantId(), UUID.randomUUID()))
				.isInstanceOf(AssignQuestConstraintViolationException.class)
				.hasMessageContaining(AssignQuestErrorCode.ALREADY_COMPLETED.getMessage());
		}
	}
}
