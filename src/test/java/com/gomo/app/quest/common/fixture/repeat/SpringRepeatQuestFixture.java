package com.gomo.app.quest.common.fixture.repeat;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;

/**
 * 반복 퀘스트 픽스처
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 */
@Component
public class SpringRepeatQuestFixture {

	private static final String ID = "a49f544f-d816-11ef-969c-6f84f91c1c7d";
	private static final String PARTICIPANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String SUBJECT_ID = "90a387a7-d7c5-11ef-b4d7-079c7dc41274";
	private static final String SUBJECT_NAME = "Spring";
	private static final QuestType QUEST_TYPE = QuestType.DAILY;
	private static final String CONTENT = "Spring 공식 문서 학습하고 TIL 작성하기";
	private static final int DISPLAY_ORDER = 2;

	public static RepeatQuest spring() {
		return RepeatQuest.of(
			RepeatQuestId.of(UUID.fromString(ID)),
			Quest.of(
				ParticipantId.of(UUID.fromString(PARTICIPANT_ID)),
				SubjectId.of(UUID.fromString(SUBJECT_ID)),
				SubjectName.of(SUBJECT_NAME),
				QUEST_TYPE,
				QuestContent.of(CONTENT)
			),
			DisplayOrder.of(DISPLAY_ORDER)
		);
	}

	public static UUID id() {
		return UUID.fromString(ID);
	}

	public static UUID participantId() {
		return UUID.fromString(PARTICIPANT_ID);
	}

	public static UUID subjectId() {
		return UUID.fromString(SUBJECT_ID);
	}

	public static QuestType questType() {
		return QuestType.DAILY;
	}

	public static String content() {
		return CONTENT;
	}

	public static int displayOrder() {
		return DISPLAY_ORDER;
	}
}
