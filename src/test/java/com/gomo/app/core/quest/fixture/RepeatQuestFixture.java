package com.gomo.app.core.quest.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

public class RepeatQuestFixture {

	public static RepeatQuest create() {
		return RepeatQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(1)
		);
	}

	public static RepeatQuest create(QuestType type) {
		return RepeatQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				type,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(1)
		);
	}

	public static RepeatQuest create(int displayOrder) {
		return RepeatQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(displayOrder)
		);
	}

	public static RepeatQuest create(UUID participantId, int displayOrder) {
		return RepeatQuest.of(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(displayOrder)
		);
	}
}
