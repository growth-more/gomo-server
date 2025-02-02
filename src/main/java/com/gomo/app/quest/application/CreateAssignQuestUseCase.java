package com.gomo.app.quest.application;

import static com.gomo.app.quest.exception.AssignQuestErrorCode.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.util.DateRangeCalculator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.AssignQuestThresholdExceededException;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;
import com.gomo.app.quest.presentation.response.CreateAssignQuestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateAssignQuestUseCase {

	private final MemberRepository memberRepository;
	private final AssignQuestRepository assignQuestRepository;

	public CreateAssignQuestResponse create(ParticipantId participantId, CreateAssignQuestRequest request) {
		QuestType questType = request.getQuestType();
		int participatingQuestCount = countParticipatingQuest(participantId, questType);
		boolean canCreate = isBelowAssignQuestThreshold(participantId, questType, participatingQuestCount);

		if(canCreate) {
			AssignQuest savedAssignQuest = assignQuestRepository.save(createAssignQuest(participantId, request, participatingQuestCount));
			return CreateAssignQuestResponse.of(savedAssignQuest.getId());
		}

		throw new AssignQuestThresholdExceededException(THRESHOLD_EXCEEDED);
	}

	@NotNull
	private static AssignQuest createAssignQuest(ParticipantId participantId, CreateAssignQuestRequest request, int currentCount) {
		AssignQuestId assignQuestId = AssignQuestId.of(UUIDGenerator.generate());
		return AssignQuest.of(
			assignQuestId,
			Quest.of(
				participantId,
				SubjectId.of(request.getSubjectId()),
				SubjectName.of(request.getSubjectName()),
				request.getQuestType(),
				QuestContent.of(request.getContent())
			),
			false,
			DisplayOrder.of(currentCount + 1),
			LocalDateTime.now()
		);
	}

	private boolean isBelowAssignQuestThreshold(ParticipantId participantId, QuestType questType, int currentCount) {
		Member member = memberRepository.findById(MemberId.of(participantId.getId()))
			.orElseThrow(() -> new IllegalArgumentException("Member not found"));
		return member.isBelowQuestThreshold(questType.name(), currentCount);
	}

	private int countParticipatingQuest(ParticipantId participantId, QuestType questType) {
		LocalDate now = LocalDate.now();
		LocalDateTime startDateTime = DateRangeCalculator.startOf(now, questType.name());
		LocalDateTime endDateTime = DateRangeCalculator.endOf(now, questType.name());

		return (int)assignQuestRepository.countByQuestParticipantIdAndQuestTypeAndStartDateTimeBetween(
			participantId,
			questType,
			startDateTime,
			endDateTime
		);
	}
}
