package com.gomo.app.quest.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.AssignQuestConstraintViolationException;
import com.gomo.app.quest.exception.AssignQuestNotFoundException;
import com.gomo.app.quest.exception.code.AssignQuestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AssignQuestService {

	private final MemberService memberService;
	private final AssignQuestRepository assignQuestRepository;

	public AssignQuest create(ParticipantId participantId, Quest quest) {
		int participatingQuestCount = countParticipatingQuest(participantId, quest.getType());
		ensureNotExceedQuestThreshold(participantId, quest.getType(), participatingQuestCount);

		int displayOrder = findMaxDisplayOrderOfParticipatingQuest(participantId, quest.getType()) + 1;
		return assignQuestRepository.save(createAssignQuest(quest, displayOrder));
	}

	public AssignQuest find(AssignQuestId assignQuestId) {
		return assignQuestRepository.findById(assignQuestId)
			.orElseThrow(() -> new AssignQuestNotFoundException(AssignQuestErrorCode.NOT_FOUND));
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
		// TODO <jhl221123>: 응용 서비스에서 회원 조회 -> Participant 전환 -> 퀘스트 도메인 영역으로 전달 하도록 수정이 필요합니다.
		Member member = memberService.find(MemberId.of(participantId.getId()));
		if (member.hasReachedQuestThreshold(questType.name(), currentCount)) {
			throw new AssignQuestConstraintViolationException(AssignQuestErrorCode.THRESHOLD_EXCEEDED);
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
