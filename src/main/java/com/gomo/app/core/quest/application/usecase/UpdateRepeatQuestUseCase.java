package com.gomo.app.core.quest.application.usecase;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.quest.application.port.command.UpdateRepeatQuestCommand;
import com.gomo.app.core.quest.domain.model.quest.QuestContent;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.model.repeat.RepeatQuest;
import com.gomo.app.core.quest.domain.model.subject.SubjectName;
import com.gomo.app.core.quest.domain.service.RepeatQuestService;
import com.gomo.app.core.quest.exception.QuestTypeConstraintViolationException;
import com.gomo.app.core.quest.exception.code.QuestTypeErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class UpdateRepeatQuestUseCase {

	private final RepeatQuestService repeatQuestService;

	@AuditLog(action = "UPDATE_REPEAT_QUEST")
	public void update(UpdateRepeatQuestCommand command) {
		RepeatQuest repeatQuest = repeatQuestService.find(command.repeatQuestId());
		repeatQuest.validateAuthority(command.participantId());
		QuestType requestedQuestType = QuestType.valueOf(command.questType());
		ensureSameQuestType(repeatQuest, requestedQuestType);

		repeatQuest.updateQuest(
			command.subjectId(),
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
