package com.gomo.app.quest.application;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateAssignQuestUseCase {

	private final AssignQuestService assignQuestService;

	// TODO <jhl221123>: 응용 영역에서 MemberService를 사용해 Member를 조회하고, Participant로 변경해 도메인 영역으로 전달해야 좋은 설계입니다.
	public CreateAssignQuestResponse create(ParticipantId participantId, CreateAssignQuestRequest request) {
		Quest quest = createQuest(participantId, request);
		AssignQuest savedAssignQuest = assignQuestService.create(participantId, quest);
		return CreateAssignQuestResponse.of(savedAssignQuest.getId());
	}

	@NotNull
	private Quest createQuest(ParticipantId participantId, CreateAssignQuestRequest request) {
		return Quest.of(
			participantId,
			SubjectId.of(request.getSubjectId()),
			SubjectName.of(request.getSubjectName()),
			request.getQuestType(),
			QuestContent.of(request.getContent())
		);
	}
}
