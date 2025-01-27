package com.gomo.app.quest.common.fixture.assign;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.CompletionProof;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;

/**
 * 할당 퀘스트 픽스처
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 */
@Component
public class JavaAssignQuestFixture {

	private static final String ID = "172916b7-d7f6-11ef-b7fd-0bd5a273f618";
	private static final String PARTICIPANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String SUBJECT_ID = "f8c51811-d7c5-11ef-82dc-4322ccc3e338";
	private static final String SUBJECT_NAME = "Java";
	private static final QuestType QUEST_TYPE = QuestType.DAILY;
	private static final String CONTENT = "Java Stream API 학습하고 TIL 작성하기";
	private static final String PROOF = null;
	private static final boolean IS_CONFIRMED = false;
	private static final boolean IS_COMPLIED = false;
	private static final int DISPLAY_ORDER = 1;
	private static final LocalDateTime STARTED_DATE_TIME = LocalDateTime.parse("2025-01-21T22:53:22.980610300");
	private static final LocalDateTime COMPLETED_DATE_TIME = null;

	public static AssignQuest java() {
		return new AssignQuest(
			AssignQuestId.of(UUID.fromString(ID)),
			Quest.of(
				ParticipantId.of(UUID.fromString(PARTICIPANT_ID)),
				SubjectId.of(UUID.fromString(SUBJECT_ID)),
				SubjectName.of(SUBJECT_NAME),
				QUEST_TYPE,
				QuestContent.of(CONTENT)),
			CompletionProof.of(PROOF),
			IS_CONFIRMED,
			IS_COMPLIED,
			DisplayOrder.of(DISPLAY_ORDER),
			STARTED_DATE_TIME,
			COMPLETED_DATE_TIME
		);
	}

	public static String id() {
		return ID;
	}

	public static String participantId() {
		return PARTICIPANT_ID;
	}

	public static UUID subjectId() {
		return UUID.fromString(SUBJECT_ID);
	}

	public static String interestName() {
		return SUBJECT_NAME;
	}

	public static QuestType questType() {
		return QUEST_TYPE;
	}

	public static String questContent() {
		return CONTENT;
	}

	public static int displayOrder() {
		return DISPLAY_ORDER;
	}
}
