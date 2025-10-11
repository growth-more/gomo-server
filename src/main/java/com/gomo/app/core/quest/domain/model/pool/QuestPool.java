package com.gomo.app.core.quest.domain.model.pool;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.arch.Authorizable;
import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.common.jpa.BaseAudit;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.quest.Quest;
import com.gomo.app.core.quest.exception.QuestAccessDeniedException;
import com.gomo.app.core.quest.exception.code.QuestErrorCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class QuestPool extends BaseAudit implements Authorizable {

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

	@Enumerated(EnumType.STRING)
	private ProcessingStatus processingStatus;

	@Enumerated(EnumType.STRING)
	private SourceType sourceType;

	protected QuestPool() {
	}

	private QuestPool(UUID id, Quest quest, ProcessingStatus processingStatus, SourceType sourceType) {
		this.id = id;
		this.quest = quest;
		this.processingStatus = processingStatus;
		this.sourceType = sourceType;
	}

	public static QuestPool of(UUID id, Quest quest, ProcessingStatus processingStatus, SourceType sourceType) {
		return new QuestPool(id, quest, processingStatus, sourceType);
	}

	public void updateProcessingStatus(ProcessingStatus processingStatus) {
		this.processingStatus = processingStatus;
	}

	public AssignQuest createAssignQuest(DisplayOrder displayOrder, LocalDateTime startDateTime) {
		return AssignQuest.of(UUIDGenerator.generate(), this.quest.copy(), false, displayOrder, startDateTime);
	}

	@Override
	public void validateAuthority(UUID accessorId) {
		if (!this.quest.isAccessibleBy(accessorId)) {
			// todo jhl221123: 할당, 반복 퀘스트와 일관성 방향 고민 필요
			throw new QuestAccessDeniedException(QuestErrorCode.ACCESS_DENIED);
		}
	}
}
