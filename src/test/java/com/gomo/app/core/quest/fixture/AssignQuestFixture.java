package com.gomo.app.core.quest.fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.assign.CompletionProof;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

public class AssignQuestFixture {

	public static AssignQuest create() {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(QuestType questType) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				questType,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(UUID participantId, QuestType questType, LocalDateTime startDateTime) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				questType,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(1),
			startDateTime
		);
	}

	public static AssignQuest create(boolean isConfirm) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			isConfirm,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(UUID participantId, boolean isConfirm) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			isConfirm,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(UUID participantId, boolean isConfirm, LocalDateTime startDateTime, int displayOrder) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			isConfirm,
			DisplayOrder.of(displayOrder),
			startDateTime
		);
	}

	public static AssignQuest create(boolean isConfirmed, boolean isCompleted, CompletionProof proof) {
		return new AssignQuest(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			proof,
			isConfirmed,
			isCompleted,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(UUID participantId, boolean isCompleted, CompletionProof proof, LocalDateTime startDateTime) {
		return new AssignQuest(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			proof,
			true,
			isCompleted,
			DisplayOrder.of(1),
			startDateTime,
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(UUID participantId, QuestType questType, boolean isCompleted, LocalDateTime completedDateTime) {
		return new AssignQuest(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				questType,
				QuestContent.of("quest content")
			),
			CompletionProof.of("completed"),
			true,
			isCompleted,
			DisplayOrder.of(1),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0),
			completedDateTime
		);
	}

	public static AssignQuest create(UUID participantId, boolean isCompleted, CompletionProof proof, LocalDateTime startDateTime, LocalDateTime completedDateTime) {
		return new AssignQuest(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			proof,
			true,
			isCompleted,
			DisplayOrder.of(1),
			startDateTime,
			completedDateTime
		);
	}

	public static AssignQuest create(int displayOrder) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(displayOrder),
			LocalDateTime.of(2025, 2, 2, 12, 51, 0)
		);
	}

	public static AssignQuest create(UUID participantId, int displayOrder, LocalDateTime dateTime) {
		return AssignQuest.of(
			UUID.randomUUID(),
			Quest.of(
				participantId,
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			false,
			DisplayOrder.of(displayOrder),
			dateTime
		);
	}

	public static AssignQuest create(LocalDateTime startDateTime, LocalDateTime completedDateTime) {
		return new AssignQuest(
			UUID.randomUUID(),
			Quest.of(
				UUID.randomUUID(),
				UUID.randomUUID(),
				SubjectName.of("subject name"),
				QuestType.DAILY,
				QuestContent.of("quest content")
			),
			CompletionProof.of("proof"),
			true,
			true,
			DisplayOrder.of(1),
			startDateTime,
			completedDateTime
		);
	}
}
