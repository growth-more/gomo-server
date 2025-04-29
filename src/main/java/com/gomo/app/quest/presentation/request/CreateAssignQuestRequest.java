package com.gomo.app.quest.presentation.request;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;

import lombok.Getter;

@Getter
public class CreateAssignQuestRequest {

	private UUID subjectId;
	private String subjectName;
	private QuestType questType;
	private String content;

	private CreateAssignQuestRequest(
		UUID subjectId,
		String subjectName,
		QuestType questType,
		String content
	) {
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.questType = questType;
		this.content = content;
	}

	public static CreateAssignQuestRequest of(
		UUID subjectId,
		String subjectName,
		QuestType questType,
		String content
	){
		return new CreateAssignQuestRequest(subjectId, subjectName, questType, content);
	}

	@NotNull
	public Quest toQuest(UUID participantId) {
		return Quest.of(
			ParticipantId.of(participantId),
			SubjectId.of(this.subjectId),
			SubjectName.of(this.subjectName),
			this.questType,
			QuestContent.of(this.content)
		);
	}
}
