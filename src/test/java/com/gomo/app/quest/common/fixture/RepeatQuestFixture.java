package com.gomo.app.quest.common.fixture;

import java.util.UUID;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;

public class RepeatQuestFixture {

	public static RepeatQuest repeatQuest() {
		return RepeatQuest.of(
			RepeatQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(1)
		);
	}

	public static RepeatQuest repeatQuest(QuestType type) {
		return RepeatQuest.of(
			RepeatQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				type,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(1)
		);
	}

	public static RepeatQuest repeatQuest(int displayOrder) {
		return RepeatQuest.of(
			RepeatQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(displayOrder)
		);
	}
}
