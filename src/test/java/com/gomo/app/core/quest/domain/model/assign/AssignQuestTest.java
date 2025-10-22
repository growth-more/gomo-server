package com.gomo.app.core.quest.domain.model.assign;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.core.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.core.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.exception.code.QuestTypeErrorCode;

@DisplayName("[Domain unit]: 할당 퀘스트 생성 및 수정 테스트")
public class AssignQuestTest {

	private static final UUID ID = UUID.randomUUID();
	private static final UUID PARTICIPANT_ID = UUID.randomUUID();
	private static final UUID SUBJECT_ID = UUID.randomUUID();
	private static final SubjectName SUBJECT_NAME = SubjectName.of("subject name");
	private static final QuestContent QUEST_CONTENT = QuestContent.of("quest content");
	private static final LocalDateTime NOW = LocalDateTime.now();

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertThat(assignQuest)
			.extracting("id", "displayOrder", "startDateTime")
			.containsExactly(ID, DisplayOrder.of(1), LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0));

		assertThat(assignQuest.getQuest())
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT);
	}

	@DisplayName("할당 퀘스트는 기본적으로 확정되지 않은 상태로 생성된다.")
	@Test
	void create_assign_quest_with_not_confirm() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertThat(assignQuest.isConfirmed()).isFalse();
	}

	@DisplayName("할당 퀘스트는 기본적으로 완료되지 않은 상태로 생성된다.")
	@Test
	void create_assign_quest_with_not_complete() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);
		CompletionProof defaultProof = CompletionProof.createDefault();

		assertThat(assignQuest.isCompleted()).isFalse();
		assertThat(assignQuest.getProof()).isEqualTo(defaultProof);
		assertThat(assignQuest.getCompletedDateTime()).isNull();
	}

	@DisplayName("할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		UUID updatedSubjectId = UUID.randomUUID();
		SubjectName updatedSubjectName = SubjectName.of("updated subject name");
		QuestContent updatedContent = QUEST_CONTENT.update("updated quest content");
		assignQuest.updateQuest(
			updatedSubjectId,
			updatedSubjectName,
			QuestType.WEEKLY,
			updatedContent
		);

		assertThat(assignQuest.getQuest())
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, updatedSubjectId, updatedSubjectName, QuestType.WEEKLY, updatedContent);
	}

	@DisplayName("퀘스트 복제 방식으로 할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest_by_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);
		UUID updatedSubjectId = UUID.randomUUID();
		SubjectName updatedSubjectName = SubjectName.of("updated subject name");
		QuestContent updatedContent = QUEST_CONTENT.update("updated quest content");
		Quest quest = Quest.of(assignQuest.participantId(), updatedSubjectId, updatedSubjectName, QuestType.WEEKLY, updatedContent);

		assignQuest.updateQuest(quest);

		assertThat(assignQuest.getQuest())
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, updatedSubjectId, updatedSubjectName, QuestType.WEEKLY, updatedContent);
	}

	@DisplayName("할당 퀘스트를 확정한다.")
	@Test
	void confirm_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assignQuest.confirm();

		assertThat(assignQuest.isConfirmed()).isTrue();
	}

	@DisplayName("퀘스트 증명과 함께 할당 퀘스트를 완료한다.")
	@Test
	void complete_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		CompletionProof updatedProof = CompletionProof.of("updated proof");
		assignQuest.complete(updatedProof, NOW);

		assertThat(assignQuest.isCompleted()).isTrue();
		assertThat(assignQuest.getProof()).isEqualTo(updatedProof);
	}

	@DisplayName("할당 퀘스트를 완료하면, 정렬 순서가 1000 증가한다.")
	@Test
	void complete_assign_quest_with_increasing_display_order() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		CompletionProof updatedProof = CompletionProof.of("updated proof");
		assignQuest.complete(updatedProof, NOW);

		assertThat(assignQuest.getDisplayOrder()).isEqualTo(DisplayOrder.of(1001));
	}

	@DisplayName("확정되지 않은 퀘스트는 완료할 수 없다.")
	@Test
	void complete_assign_quest_with_unconfirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		CompletionProof updatedProof = CompletionProof.of("updated proof");

		assertThatThrownBy(() -> assignQuest.complete(updatedProof, NOW))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.NOT_CONFIRMED.getMessage());
	}

	@DisplayName("이미 완료된 퀘스트는 중복해서 완료할 수 없다.")
	@Test
	void complete_assign_quest_with_completed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		CompletionProof updatedProof = CompletionProof.of("updated proof");
		assignQuest.complete(updatedProof, NOW);

		assertThatThrownBy(() -> assignQuest.complete(updatedProof, NOW))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_COMPLETED.getMessage());
	}

	@DisplayName("퀘스트 타입 확인 결과, 할당 퀘스트와 같은 타입이다.")
	@Test
	void quest_type_is_same_assign_quest_type() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assignQuest.ensureSameQuestType(QuestType.DAILY);
	}

	@DisplayName("퀘스트 타입 확인 결과, 할당 퀘스트와 다른 타입이다.")
	@Test
	void quest_type_is_not_same_assign_quest_type() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertThatThrownBy(() -> assignQuest.ensureSameQuestType(QuestType.WEEKLY))
			.isInstanceOf(QuestTypeConstraintViolationException.class)
			.hasMessageContaining(QuestTypeErrorCode.MISMATCHED.getMessage());
	}

	@DisplayName("확정하지 않은 할당 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_display_order_unconfirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assignQuest.changeOrder(DisplayOrder.of(2));

		assertThat(assignQuest.getDisplayOrder()).isEqualTo(DisplayOrder.of(2));
	}

	@DisplayName("확정된 할당 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_display_order_with_confirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assignQuest.changeOrder(DisplayOrder.of(2));

		assertThat(assignQuest.getDisplayOrder()).isEqualTo(DisplayOrder.of(2));
	}

	@DisplayName("이미 완료된 할당 퀘스트는 정렬 순서를 변경할 수 없다.")
	@Test
	void update_display_order_with_completed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		CompletionProof updatedProof = CompletionProof.of("updated proof");
		assignQuest.complete(updatedProof, NOW);

		assertThatThrownBy(() -> assignQuest.changeOrder(DisplayOrder.of(2)))
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.NOT_ALLOWED_ORDER_CHANGE.getMessage());
	}

	@DisplayName("할당 퀘스트는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertDoesNotThrow(() -> assignQuest.validateAuthority(PARTICIPANT_ID));
	}

	@DisplayName("할당 퀘스트는 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertThatThrownBy(() -> assignQuest.validateAuthority(UUID.randomUUID()))
			.isInstanceOf(AssignQuestAccessDeniedException.class)
			.hasMessageContaining(AssignQuestErrorCode.ACCESS_DENIED.getMessage());
	}

	@DisplayName("확정 여부 확인 결과, 아직 확정되지 않은 퀘스트임을 확인한다.")
	@Test
	void ensure_not_confirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertDoesNotThrow(assignQuest::ensureNotConfirmed);
	}

	@DisplayName("확정 여부 확인 결과, 이미 확정된 퀘스트임을 확인한다.")
	@Test
	void already_confirmed_assign_quest() {
		AssignQuest assignQuest = AssignQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertThatThrownBy(assignQuest::ensureNotConfirmed)
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_CONFIRMED.getMessage());
	}

	@DisplayName("완료 여부 확인 결과, 아직 완료되지 않은 퀘스트임을 확인한다.")
	@Test
	void ensure_not_completed_assign_quest() {
		AssignQuest assignQuest = new AssignQuest(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			CompletionProof.createDefault(),
			false,
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertDoesNotThrow(assignQuest::ensureNotCompleted);
	}

	@DisplayName("완료 여부 확인 결과, 이미 완료된 퀘스트임을 확인한다.")
	@Test
	void already_completed_assign_quest() {
		AssignQuest assignQuest = new AssignQuest(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			CompletionProof.createDefault(),
			true,
			true,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0),
			LocalDateTime.of(2025, 1, 31, 0, 0, 0, 0)
		);

		assertThatThrownBy(assignQuest::ensureNotCompleted)
			.isInstanceOf(AssignQuestConstraintViolationException.class)
			.hasMessageContaining(AssignQuestErrorCode.ALREADY_COMPLETED.getMessage());
	}
}
