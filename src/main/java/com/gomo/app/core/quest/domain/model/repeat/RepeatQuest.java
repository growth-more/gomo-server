package com.gomo.app.core.quest.domain.model.repeat;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.exception.RepeatQuestAccessDeniedException;
import com.gomo.app.core.quest.exception.code.RepeatQuestErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class RepeatQuest extends BaseAudit implements OrderChangeable, Authorizable {

	@Id
	private UUID id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "participantId", column = @Column(name = "participant_id")),
		@AttributeOverride(name = "subjectId", column = @Column(name = "subject_id")),
		@AttributeOverride(name = "subjectName", column = @Column(name = "subject_name")),
		@AttributeOverride(name = "type", column = @Column(name = "quest_type")),
		@AttributeOverride(name = "content", column = @Column(name = "content"))
	})
	private Quest quest;

	@Embedded
	private DisplayOrder displayOrder;

	protected RepeatQuest() {
	}

	private RepeatQuest(UUID id, Quest quest, DisplayOrder displayOrder) {
		this.id = id;
		this.quest = quest;
		this.displayOrder = displayOrder;
	}

	public static RepeatQuest of(UUID id, Quest quest, DisplayOrder displayOrder) {
		return new RepeatQuest(id, quest, displayOrder);
	}

	public void updateQuest(UUID subjectId, SubjectName subjectName, QuestType questType, QuestContent content) {
		this.quest = this.quest.update(subjectId, subjectName, questType, content);
	}

	public AssignQuest createAssignQuest(DisplayOrder displayOrder, LocalDateTime startDateTime) {
		return AssignQuest.of(UUIDGenerator.generate(), this.quest.copy(), true, displayOrder, startDateTime);
	}

	public boolean isSameQuestType(QuestType questType) {
		return this.quest.getType() == questType;
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if (!this.quest.isAccessibleBy(accessorId)) {
			throw new RepeatQuestAccessDeniedException(RepeatQuestErrorCode.ACCESS_DENIED);
		}
	}
}
