package com.gomo.app.quest.unit.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.exception.RepeatQuestAccessDeniedException;

@DisplayName("[Domain unit]: 반복 퀘스트 생성 및 수정 테스트")
public class RepeatQuestTest {

	private static final RepeatQuestId ID = RepeatQuestId.of(UUID.randomUUID());
	private static final ParticipantId PARTICIPANT_ID = ParticipantId.of(UUID.randomUUID());
	private static final SubjectId SUBJECT_ID = SubjectId.of(UUID.randomUUID());
	private static final SubjectName SUBJECT_NAME = SubjectName.of("subject name");
	private static final QuestContent QUEST_CONTENT = QuestContent.of("quest content");

	@DisplayName("반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		assertThat(repeatQuest)
			.extracting("id", "quest", "displayOrder")
			.containsExactly(ID, repeatQuest.getQuest(), DisplayOrder.of(1));

		assertThat(repeatQuest.getQuest())
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT);
	}

	@DisplayName("반복 퀘스트를 수정한다.")
	@Test
	void update_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		SubjectId updatedSubjectId = SubjectId.of(UUID.randomUUID());
		SubjectName updatedSubjectName = SubjectName.of("updated subject name");
		QuestContent updatedContent = QUEST_CONTENT.update("updated quest content");
		repeatQuest.updateQuest(updatedSubjectId, updatedSubjectName, QuestType.WEEKLY, updatedContent);

		assertThat(repeatQuest.getQuest())
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, updatedSubjectId, updatedSubjectName, QuestType.WEEKLY, updatedContent);
	}

	@DisplayName("할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		DisplayOrder assignQuestDisplayOrder = DisplayOrder.of(1);
		LocalDateTime startDateTime = LocalDateTime.now();
		AssignQuest assignQuest = repeatQuest.createAssignQuest(assignQuestDisplayOrder, startDateTime);

		assertThat(assignQuest)
			.extracting("displayOrder", "startDateTime")
			.containsExactly(assignQuestDisplayOrder, startDateTime);

		assertThat(assignQuest.getQuest())
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT);
	}

	@DisplayName("반복 퀘스트로 생성된 할당 퀘스트는 확정된 상태로 생성된다.")
	@Test
	void create_assign_quest_with_confirm() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		DisplayOrder assignQuestDisplayOrder = DisplayOrder.of(1);
		LocalDateTime startDateTime = LocalDateTime.now();
		AssignQuest assignQuest = repeatQuest.createAssignQuest(assignQuestDisplayOrder, startDateTime);


		assertThat(assignQuest.isConfirmed()).isTrue();
	}

	@DisplayName("할당 퀘스트의 타입을 같은 타입과 비교한다.")
	@Test
	void quest_type_is_same_repeat_quest_type() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		boolean actual = repeatQuest.isSameQuestType(QuestType.DAILY);

		assertThat(actual).isTrue();
	}

	@DisplayName("할당 퀘스트의 타입을 다른 타입과 비교한다.")
	@Test
	void quest_type_is_not_same_repeat_quest_type() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		boolean actual = repeatQuest.isSameQuestType(QuestType.WEEKLY);

		assertThat(actual).isFalse();
	}

	@DisplayName("반복 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_display_order_with_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		repeatQuest.changeOrder(DisplayOrder.of(2));

		assertThat(repeatQuest.getDisplayOrder()).isEqualTo(DisplayOrder.of(2));
	}

	@DisplayName("반복 퀘스트는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		assertDoesNotThrow(() -> repeatQuest.validateAuthority(PARTICIPANT_ID.getId()));
	}

	@DisplayName("반복 퀘스트는 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_repeat_quest() {
		RepeatQuest repeatQuest = RepeatQuest.of(
			ID,
			Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QuestType.DAILY, QUEST_CONTENT),
			DisplayOrder.of(1)
		);

		assertThatThrownBy(() -> repeatQuest.validateAuthority(UUID.randomUUID()))
			.isInstanceOf(RepeatQuestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the repeat quest");
	}
}
