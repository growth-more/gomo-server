package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.quest.application.port.command.UpdateRepeatQuestCommand;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.quest.exception.code.QuestTypeErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateRepeatQuestUseCase {

	private final RepeatQuestService repeatQuestService;

	@AuditLog(action = "UPDATE_REPEAT_QUEST")
	public void update(UpdateRepeatQuestCommand command) {
		RepeatQuest repeatQuest = repeatQuestService.find(RepeatQuestId.of(command.repeatQuestId()));
		repeatQuest.validateAuthority(command.participantId());
		QuestType requestedQuestType = QuestType.valueOf(command.questType());
		ensureSameQuestType(repeatQuest, requestedQuestType);

		repeatQuest.updateQuest(
			SubjectId.of(command.subjectId()),
			SubjectName.of(command.subjectName()),
			requestedQuestType,
			QuestContent.of(command.content())
		);
	}

	private void ensureSameQuestType(RepeatQuest repeatQuest, QuestType questType) {
		if (!repeatQuest.isSameQuestType(questType)) {
			throw new QuestTypeConstraintViolationException(QuestTypeErrorCode.MISMATCHED);
		}
	}
}
