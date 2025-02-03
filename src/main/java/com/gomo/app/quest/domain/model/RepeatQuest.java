package com.gomo.app.quest.domain.model;

import java.util.UUID;

import com.gomo.app.common.domain.Authorizable;
import com.gomo.app.common.domain.BaseAudit;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.OrderChangeable;
import com.gomo.app.interest.domain.model.InterestId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class RepeatQuest extends BaseAudit implements OrderChangeable, Authorizable {

	@EmbeddedId
	private RepeatQuestId id;

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

	protected RepeatQuest() {}

	private RepeatQuest(
		RepeatQuestId id,
		Quest quest,
		DisplayOrder displayOrder
	) {
		this.id = id;
		this.quest = quest;
		this.displayOrder = displayOrder;
	}

	public static RepeatQuest of(
		RepeatQuestId id,
		Quest quest,
		DisplayOrder displayOrder
	) {
		return new RepeatQuest(id, quest, displayOrder);
	}

	public void update(InterestId interestId, QuestType questType, QuestContent questContent) {
	}

	public AssignQuest createAssignQuest() {
		return null;
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {}

	@Override
	public void validateAuthority(UUID accessorId) {

	}
}
