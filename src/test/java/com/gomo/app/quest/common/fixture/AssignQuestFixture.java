package com.gomo.app.quest.common.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.CompletionProof;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;

public class AssignQuestFixture {

	public static AssignQuest assignQuest() {
		return AssignQuest.of(
			AssignQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest assignQuest(QuestType questType) {
		return AssignQuest.of(
			AssignQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				questType,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest assignQuest(boolean isConfirm) {
		return AssignQuest.of(
			AssignQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			isConfirm,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest assignQuest(boolean isCompleted, CompletionProof proof) {
		return new AssignQuest(
			AssignQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			proof,
			true,
			isCompleted,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest assignQuest(int displayOrder) {
		return AssignQuest.of(
			AssignQuestId.of(UUID.randomUUID()),
			Quest.of(
				ParticipantId.of(UUID.randomUUID()),
				SubjectId.of(UUID.randomUUID()),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(displayOrder),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}
}
