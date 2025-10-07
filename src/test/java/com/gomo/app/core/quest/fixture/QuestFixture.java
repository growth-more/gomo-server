package com.gomo.app.core.quest.fixture;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectId;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

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
