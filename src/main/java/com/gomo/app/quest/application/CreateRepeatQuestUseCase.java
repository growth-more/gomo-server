package com.gomo.app.quest.application;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateRepeatQuestUseCase {

	private final RepeatQuestService repeatQuestService;

	public CreateRepeatQuestResponse create(ParticipantId participantId, CreateRepeatQuestRequest request) {
		Quest quest = createQuest(participantId, request);
		RepeatQuest savedRepeatQuest = repeatQuestService.create(participantId, quest);
		return CreateRepeatQuestResponse.of(savedRepeatQuest.getId());
	}

	@NotNull
	private Quest createQuest(ParticipantId participantId, CreateRepeatQuestRequest request) {
		return Quest.of(
			participantId,
			SubjectId.of(request.getSubjectId()),
			SubjectName.of(request.getSubjectName()),
			request.getQuestType(),
			QuestContent.of(request.getContent())
		);
	}
}
