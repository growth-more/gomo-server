package com.gomo.app.quest.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.quest.application.translator.ParticipantTranslator;
import com.gomo.app.quest.domain.model.Participant;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.domain.service.RepeatQuestService;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;
import com.gomo.app.quest.presentation.response.CreateRepeatQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateRepeatQuestUseCase {

	private final MemberService memberService;
	private final RepeatQuestService repeatQuestService;
	private final RepeatQuestRepository repeatQuestRepository;

	public CreateRepeatQuestResponse create(UUID participantId, CreateRepeatQuestRequest request) {
		ensureNotExceedQuestQuota(participantId, request.getQuestType());
		Quest quest = request.toQuest(participantId);
		RepeatQuest savedRepeatQuest = repeatQuestService.create(ParticipantId.of(participantId), quest);
		return CreateRepeatQuestResponse.of(savedRepeatQuest.getId());
	}

	private void ensureNotExceedQuestQuota(UUID participantId, QuestType questType) {
		Participant participant = ParticipantTranslator.from(memberService.find(MemberId.of(participantId)));
		int repeatQuestCount = (int) repeatQuestRepository.countByQuestParticipantIdAndQuestType(participant.getId(), questType);
		participant.validateQuestQuota(questType, repeatQuestCount);
	}
}
