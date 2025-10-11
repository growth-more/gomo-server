package com.gomo.app.core.quest.domain.model.assign;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.displayorder.OrderChangeable;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.exception.AssignQuestAccessDeniedException;
import com.gomo.app.core.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.core.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.exception.code.AssignQuestErrorCode;
import com.gomo.app.core.quest.exception.code.QuestTypeErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class AssignQuest extends BaseAudit implements OrderChangeable, Authorizable {

	private static final boolean NOT_COMPLETED = false;

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

	protected AssignQuest() {
	}

	public AssignQuest(UUID id, Quest quest, CompletionProof proof, boolean isConfirmed, boolean isCompleted, DisplayOrder displayOrder,
		LocalDateTime startDateTime, LocalDateTime completedDateTime) {
		this.id = id;
		this.quest = quest;
		this.proof = proof;
		this.isConfirmed = isConfirmed;
		this.isCompleted = isCompleted;
		this.displayOrder = displayOrder;
		this.startDateTime = startDateTime;
		this.completedDateTime = completedDateTime;
	}

	public static AssignQuest of(UUID id, Quest quest, boolean isConfirmed, DisplayOrder displayOrder, LocalDateTime startDateTime) {
		return new AssignQuest(id, quest, CompletionProof.createDefault(), isConfirmed, NOT_COMPLETED, displayOrder, startDateTime, null);
	}

	public UUID participantId() {
		return this.quest.getParticipantId();
	}

	public UUID subjectId() {
		return this.quest.getSubjectId();
	}

	public String subjectName() {
		return this.quest.getSubjectName().getSubjectName();
	}

	public QuestType questType() {
		return this.quest.getType();
	}

	public String content() {
		return this.quest.getContent().getQuestContent();
	}

	public int displayOrder() {
		return this.getDisplayOrder().getDisplayOrder();
	}

	public void updateQuest(UUID subjectId, SubjectName subjectName, QuestType questType, QuestContent content) {
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
		if (!this.quest.getType().equals(questType)) {
			throw new QuestTypeConstraintViolationException(QuestTypeErrorCode.MISMATCHED);
		}
	}

	@Override
	public void changeOrder(DisplayOrder displayOrder) {
		if (this.isCompleted) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.NOT_ALLOWED_ORDER_CHANGE);
		}
		this.displayOrder = displayOrder;
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if (!this.quest.isAccessibleBy(accessorId)) {
			throw new AssignQuestAccessDeniedException(AssignQuestErrorCode.ACCESS_DENIED);
		}
	}

	private void ensureConfirmed() {
		if (!this.isConfirmed) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.NOT_CONFIRMED);
		}
	}

	public void ensureNotConfirmed() {
		if (this.isConfirmed) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.ALREADY_CONFIRMED);
		}
	}

	public void ensureNotCompleted() {
		if (this.isCompleted) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.ALREADY_COMPLETED);
		}
	}
}
