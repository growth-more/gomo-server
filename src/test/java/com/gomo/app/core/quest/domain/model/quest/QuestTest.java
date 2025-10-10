package com.gomo.app.core.quest.domain.model.quest;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.subject.SubjectId;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

@DisplayName("[Domain unit]: 퀘스트 생성 및 수정 테스트")
public class QuestTest {

	private static final ParticipantId PARTICIPANT_ID = ParticipantId.of(UUID.randomUUID());
	private static final SubjectId SUBJECT_ID = SubjectId.of(UUID.randomUUID());
	private static final SubjectName SUBJECT_NAME = SubjectName.of("subject name");
	private static final QuestType QUEST_TYPE = QuestType.DAILY;
	private static final QuestContent QUEST_CONTENT = QuestContent.of("question content");

	@DisplayName("퀘스트를 생성한다.")
	@Test
	void create_quest() {
		Quest quest = Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QUEST_TYPE, QUEST_CONTENT);

		assertThat(quest)
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QUEST_TYPE, QUEST_CONTENT);
	}

	@DisplayName("퀘스트를 복사한다.")
	@Test
	void copy_quest() {
		Quest quest = Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QUEST_TYPE, QUEST_CONTENT);
		Quest copiedQuest = quest.copy();

		assertThat(copiedQuest)
			.extracting("participantId", "subjectId", "subjectName", "type", "content")
			.containsExactly(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QUEST_TYPE, QUEST_CONTENT);
	}

	@DisplayName("퀘스트 참여자는 접근 권한이 있다.")
	@Test
	void check_access_authority_by_participant() {
		Quest quest = Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QUEST_TYPE, QUEST_CONTENT);
		boolean actual = quest.isAccessibleBy(PARTICIPANT_ID.getId());

		assertThat(actual).isTrue();
	}

	@DisplayName("퀘스트 참여자가 아니면 접근 권한이 없다.")
	@Test
	void check_access_authority_by_non_participant() {
		Quest quest = Quest.of(PARTICIPANT_ID, SUBJECT_ID, SUBJECT_NAME, QUEST_TYPE, QUEST_CONTENT);
		boolean actual = quest.isAccessibleBy(UUID.randomUUID());

		assertThat(actual).isFalse();
	}
}
