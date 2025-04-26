package com.gomo.app.quest.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.Authorizable;
import com.gomo.app.common.BaseAudit;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.quest.exception.code.QuestTypeErrorCode;

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
		@AttributeOverride(name = "participantId", column = @Column(name = "participant_id")),
		@AttributeOverride(name = "subjectId", column = @Column(name = "subject_id")),
		@AttributeOverride(name = "subjectName", column = @Column(name = "subject_name")),
		@AttributeOverride(name = "type", column = @Column(name = "quest_type")),
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
			id, quest, CompletionProof.createDefault(), isConfirmed, NOT_COMPLETED, displayOrder, startDateTime, BLANK_DATE_TIME
		);
	}

	public void updateQuest(SubjectId subjectId, SubjectName subjectName, QuestType questType, QuestContent content) {
		this.quest = this.quest.update(subjectId, subjectName, questType, content);
	}

	public void confirm() {
		this.isConfirmed = true;
	}

	public void complete(CompletionProof proof, LocalDateTime completedDateTime) {
		ensureConfirmed();
		ensureNotCompleted();

		this.isCompleted = true;
		this.proof = proof;
		this.displayOrder = this.displayOrder.increase(1000);
		this.completedDateTime = completedDateTime;
	}

	public void ensureSameQuestType(QuestType questType) {
		if(!this.quest.getType().equals(questType)) {
			throw new QuestTypeConstraintViolationException(QuestTypeErrorCode.MISMATCHED);
		}
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
		if(this.isCompleted) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.NOT_ALLOWED_ORDER_CHANGE);
		}
		this.displayOrder = displayOrder;
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if(!this.quest.isAccessibleBy(accessorId)) {
			throw new AssignQuestAccessDeniedException(AssignQuestErrorCode.ACCESS_DENIED);
		}
	}

	private void ensureConfirmed() {
		if(!this.isConfirmed) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.NOT_CONFIRMED);
		}
	}

	public void ensureNotConfirmed() {
		if(this.isConfirmed) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.ALREADY_CONFIRMED);
		}
	}

	public void ensureNotCompleted() {
		if(this.isCompleted) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.ALREADY_COMPLETED);
		}
	}
}
