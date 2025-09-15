package com.gomo.app.quest.application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.quest.application.translator.ParticipantTranslator;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.Participant;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.AssignQuestService;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateAssignQuestUseCase {

	private final MemberService memberService;
	private final AssignQuestService assignQuestService;
	private final AssignQuestRepository assignQuestRepository;

	@AuditLog(action = "CREATE_ASSIGN_QUEST")
	public CreateAssignQuestResponse create(UUID participantId, CreateAssignQuestRequest request) {
		ensureNotExceedQuestQuota(participantId, request.getQuestType());
		Quest quest = request.toQuest(participantId);
		AssignQuest savedAssignQuest = assignQuestService.create(ParticipantId.of(participantId), quest);
		return CreateAssignQuestResponse.of(savedAssignQuest.getId());
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		Participant participant = ParticipantTranslator.from(memberService.find(MemberId.of(participantId)));
		int participatingQuestCount = countParticipatingQuest(participant.getId(), questType);
		participant.validateQuestQuota(questType, participatingQuestCount);
	}

	private int countParticipatingQuest(ParticipantId participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());

		return (int)assignQuestRepository.countParticipatingQuestByQuestType(
			participantId,
			questType,
			startDateTime,
			endDateTime
		);
	}
}
