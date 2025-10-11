package com.gomo.app.core.quest.domain.model.quest;

import java.util.UUID;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
@ValueObject
public class Quest {

	private UUID participantId;
	private UUID subjectId;

	@Embedded
	private SubjectName subjectName;

	@Enumerated(EnumType.STRING)
	private QuestType type;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "questContent", column = @Column(name = "content"))
	})
	private QuestContent content;

	protected Quest() {
	}

	private Quest(UUID participantId, UUID subjectId, SubjectName subjectName, QuestType type, QuestContent content) {
		this.participantId = participantId;
		this.subjectId = subjectId;
		this.subjectName = subjectName;
		this.type = type;
		this.content = content;
	}

	public static Quest of(UUID participantId, UUID subjectId, SubjectName subjectName, QuestType type, QuestContent content) {
		return new Quest(participantId, subjectId, subjectName, type, content);
	}

	public Quest copy() {
		return Quest.of(this.participantId, this.subjectId, this.subjectName, this.type, this.content);
	}

	public Quest update(UUID subjectId, SubjectName subjectName, QuestType questType, QuestContent questContent) {
		return Quest.of(this.participantId, subjectId, subjectName, questType, questContent);
	}

	public boolean isAccessibleBy(UUID accessorId) {
		return this.participantId.equals(accessorId);
	}
}
