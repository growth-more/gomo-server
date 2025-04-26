package com.gomo.app.quest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.quest.exception.RepeatQuestNotFoundException;
import com.gomo.app.quest.exception.code.QuestTypeErrorCode;
import com.gomo.app.quest.exception.code.RepeatQuestErrorCode;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;

	public void update(UUID accessorId, RepeatQuestId repeatQuestId, UpdateRepeatQuestRequest request) {
		RepeatQuest repeatQuest = repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new RepeatQuestNotFoundException(RepeatQuestErrorCode.NOT_FOUND));
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
			throw new QuestTypeConstraintViolationException(QuestTypeErrorCode.MISMATCHED);
		}
	}
}
