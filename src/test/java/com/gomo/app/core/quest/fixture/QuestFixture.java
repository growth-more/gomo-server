package com.gomo.app.core.quest.fixture;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.model.Quest;
import com.gomo.app.core.quest.domain.model.QuestContent;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.SubjectId;
import com.gomo.app.core.quest.domain.model.SubjectName;

public class QuestFixture {

	public static Quest quest() {
		return Quest.of(
			ParticipantId.of(UUID.randomUUID()),
			SubjectId.of(UUID.randomUUID()),
			SubjectName.of("subject name"),
			QuestType.DAILY,
			QuestContent.of("quest content")
		);
	}

	public static Quest quest(UUID participantId) {
		return Quest.of(
			ParticipantId.of(participantId),
			SubjectId.of(UUID.randomUUID()),
			SubjectName.of("subject name"),
			QuestType.DAILY,
			QuestContent.of("quest content")
		);
	}
}
