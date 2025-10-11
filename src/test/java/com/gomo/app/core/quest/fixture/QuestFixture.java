package com.gomo.app.core.quest.fixture;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

public class QuestFixture {

	public static Quest create() {
		return Quest.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			SubjectName.of("subject name"),
			QuestType.DAILY,
			QuestContent.of("quest content")
		);
	}

	public static Quest create(UUID participantId) {
		return Quest.of(
			participantId,
			UUID.randomUUID(),
			SubjectName.of("subject name"),
			QuestType.DAILY,
			QuestContent.of("quest content")
		);
	}
}
