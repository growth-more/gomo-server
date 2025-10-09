package com.gomo.app.core.quest.fixture;

import java.util.UUID;

import com.gomo.app.core.quest.domain.model.participant.ParticipantId;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.QuestPoolId;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectId;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

public class QuestPoolFixture {

	public static QuestPool create() {
		return QuestPool.of(
			QuestPoolId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			ProcessingStatus.UNUSED,
			SourceType.AI
		);
	}
}
