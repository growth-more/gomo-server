package com.gomo.app.core.quest.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.quest.domain.model.ParticipantId;
import com.gomo.app.core.quest.domain.model.Quest;
import com.gomo.app.core.quest.domain.model.QuestContent;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.model.RepeatQuest;
import com.gomo.app.core.quest.domain.model.RepeatQuestId;
import com.gomo.app.core.quest.domain.model.SubjectId;
import com.gomo.app.core.quest.domain.model.SubjectName;

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

	public static RepeatQuest repeatQuest(UUID participantId, int displayOrder) {
		return RepeatQuest.of(
			RepeatQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(participantId),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			DisplayOrder.of(displayOrder)
		);
	}
}
