package com.gomo.app.quest.domain.service;

import static com.gomo.app.quest.exception.AssignQuestErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.AssignQuestThresholdExceededException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AssignQuestService {

	private final MemberRepository memberRepository;
	private final AssignQuestRepository assignQuestRepository;

	public AssignQuest create(ParticipantId participantId, Quest quest) {
		int participatingQuestCount = countParticipatingQuest(participantId, quest.getType());
		ensureNotExceedQuestThreshold(participantId, quest.getType(), participatingQuestCount);

		int displayOrder = findMaxDisplayOrderOfParticipatingQuest(participantId, quest.getType()) + 1;
		return assignQuestRepository.save(createAssignQuest(quest, displayOrder));
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

	private void ensureNotExceedQuestThreshold(ParticipantId participantId, QuestType questType, int currentCount) {
		// TODO <jhl221123>: 여기서 회원을 조회하면 안 되고, 회원 서비스에서 공통 처리되어야 한다.
		Member member = memberRepository.findById(MemberId.of(participantId.getId()))
			.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		if(member.hasReachedQuestThreshold(questType.name(), currentCount)) {
			throw new AssignQuestThresholdExceededException(THRESHOLD_EXCEEDED);
		}
	}

	@NotNull
	private AssignQuest createAssignQuest(Quest quest, int displayOrder) {
		return AssignQuest.of(
			AssignQuestId.of(UUIDGenerator.generate()),
			quest,
			false,
			DisplayOrder.of(displayOrder),
			LocalDateTime.now()
		);
	}

	private int findMaxDisplayOrderOfParticipatingQuest(ParticipantId participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());

		return assignQuestRepository.findMaxDisplayOrderOfParticipatingQuest(
			participantId,
			questType,
			startDateTime,
			endDateTime
		);
	}
}
