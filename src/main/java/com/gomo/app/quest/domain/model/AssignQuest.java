package com.gomo.app.quest.domain.model;

import java.time.LocalDateTime;
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
public class AssignQuest extends BaseAudit implements OrderChangeable, Authorizable {

	private static final boolean NOT_COMPLETED = false;
	private static final LocalDateTime BLANK_DATE_TIME = null;

	@EmbeddedId
	private AssignQuestId id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "memberId", column = @Column(name = "member_id")),
		@AttributeOverride(name = "interestId", column = @Column(name = "interest_id")),
		@AttributeOverride(name = "interestName", column = @Column(name = "interest_name")),
		@AttributeOverride(name = "content", column = @Column(name = "content"))
	})
	private Quest quest;

	@AttributeOverrides({
		@AttributeOverride(name = "url", column = @Column(name = "proof")),
	})
	private CompletionProof proof;

	private boolean isConfirmed;

	private boolean isCompleted;

	@Embedded
	private DisplayOrder displayOrder;

	private LocalDateTime startDateTime;

	private LocalDateTime completedDateTime;

	protected AssignQuest() {}

	public AssignQuest(
		AssignQuestId id,
		Quest quest,
		CompletionProof proof,
		boolean isConfirmed,
		boolean isCompleted,
		DisplayOrder displayOrder,
		LocalDateTime startDateTime,
		LocalDateTime completedDateTime
	) {
		this.id = id;
		this.quest = quest;
		this.proof = proof;
		this.isConfirmed = isConfirmed;
		this.isCompleted = isCompleted;
		this.displayOrder = displayOrder;
		this.startDateTime = startDateTime;
		this.completedDateTime = completedDateTime;
	}

	public static AssignQuest of(
		AssignQuestId id,
		Quest quest,
		boolean isConfirmed,
		DisplayOrder displayOrder,
		LocalDateTime startDateTime
	) {
		return new AssignQuest(
			id, quest, CompletionProof.blank(), isConfirmed, NOT_COMPLETED, displayOrder, startDateTime, BLANK_DATE_TIME
		);
	}

	public void updateQuest(InterestId interestId, QuestType questType, QuestContent content) {

	}

	public void confirm() {
	}

	public void complete(CompletionProof proof, QuestReward questReward) {}

	private boolean isAlreadyConfirm() {
		return this.isConfirmed;
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
	}

	@Override
	public void validateAuthority(UUID accessorId) {

	}
}
