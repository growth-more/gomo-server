package com.gomo.app.quest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateAssignQuestUseCase {

	private final AssignQuestRepository assignQuestRepository;

	public void update(UUID accessorId, AssignQuestId assignQuestId, UpdateAssignQuestRequest request) {
		AssignQuest assignQuest = assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Assign quest not found"));
		assignQuest.validateAuthority(accessorId);

		QuestType requestedQuestType = request.getQuestType();
		validateQuestType(requestedQuestType, assignQuest);
		validateAssignQuestState(assignQuest);

		assignQuest.updateQuest(
			SubjectId.of(request.getSubjectId()),
			SubjectName.of(request.getSubjectName()),
			requestedQuestType,
			QuestContent.of(request.getContent())
		);
	}

	private static void validateQuestType(QuestType questType, AssignQuest assignQuest) {
		if(!assignQuest.isSameQuestType(questType)) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_STATE, "Assign quest can only be modified within the same type");
		}
	}

	private static void validateAssignQuestState(AssignQuest assignQuest) {
		if(assignQuest.isCompleted()) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_STATE, "Assign quests cannot be modified once completed");
		}

		if(assignQuest.isConfirmed()) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_STATE, "Assign quests cannot be modified once confirmed");
		}
	}
}
