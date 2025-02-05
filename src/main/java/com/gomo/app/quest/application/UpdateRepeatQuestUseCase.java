package com.gomo.app.quest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void update(UUID accessorId, RepeatQuestId repeatQuestId, UpdateRepeatQuestRequest request) {
		RepeatQuest repeatQuest = repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "RepeatQuest not found with id: " + repeatQuestId.getId()));
		repeatQuest.validateAuthority(accessorId);

		validateQuestType(request.getQuestType(), repeatQuest);
		repeatQuest.updateQuest(
			SubjectId.of(request.getSubjectId()),
			SubjectName.of(request.getSubjectName()),
			request.getQuestType(),
			QuestContent.of(request.getContent())
		);
	}

	private static void validateQuestType(QuestType questType, RepeatQuest repeatQuest) {
		if(!repeatQuest.isSameQuestType(questType)) {
			throw new PolicyViolationException(DomainErrorCode.INVALID_STATE, "Repeat quest can only be modified within the same type");
		}
	}
}
