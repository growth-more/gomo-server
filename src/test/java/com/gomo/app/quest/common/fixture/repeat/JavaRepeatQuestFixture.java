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
public class JavaRepeatQuestFixture {

	private static final String ID = "3892ced2-d816-11ef-a05f-25caca7d3e8c";
	private static final String PARTICIPANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String SUBJECT_ID = "f8c51811-d7c5-11ef-82dc-4322ccc3e338";
	private static final String SUBJECT_NAME = "Java";
	private static final QuestType QUEST_TYPE = QuestType.DAILY;
	private static final String CONTENT = "Java 공식 문서 학습하고 TIL 작성하기";
	private static final int DISPLAY_ORDER = 1;

	public static RepeatQuest java() {
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

	public static UUID memberId() {
		return UUID.fromString(PARTICIPANT_ID);
	}

	public static UUID interestId() {
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
