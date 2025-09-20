package com.gomo.app.quest.fixture;

import java.util.UUID;

import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.ProcessingStatus;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestPool;
import com.gomo.app.quest.domain.model.QuestPoolId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SourceType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;

public class QuestPoolFixture {

	public static QuestPool questPool() {
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
