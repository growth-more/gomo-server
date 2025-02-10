package com.gomo.app.quest.domain.service;

import static com.gomo.app.quest.domain.model.QuestType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ScheduleAssignQuestService {

	private static final String BLANK_SUBJECT_ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";
	private static final String BLANK_SUBJECT_NAME = "BLANK";
	private static final String BLANK_SUBJECT_CONTENT = "BLANK";

	private final MemberRepository memberRepository;
	private final AssignQuestRepository assignQuestRepository;
	private final RepeatQuestRepository repeatQuestRepository;

	@Scheduled(cron = "0 0 2 * * ?")
	public void createParticipatingQuestOfAllActiveMember() {
		LocalDateTime startDateTime = LocalDateTime.now();
		List<Member> candidates = findActiveMember(startDateTime.toLocalDate());

		for (Member candidate : candidates) {
			ParticipantId participantId = ParticipantId.of(candidate.getId().getId());
			Map<QuestType, List<RepeatQuest>> groupedRepeatQuests = buildRepeatQuestGroupByQuestType(participantId);
			createParticipatingQuestOfCandidate(candidate, startDateTime, groupedRepeatQuests);
		}
	}

	private List<Member> findActiveMember(LocalDate startDate) {
		return memberRepository.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
			ActivateStatus.ACTIVE,
			startDate.minusDays(1).atStartOfDay()
		);
	}

	@NotNull
	private Map<QuestType, List<RepeatQuest>> buildRepeatQuestGroupByQuestType(ParticipantId participantId) {
		List<RepeatQuest> candidateRepeatQuests = repeatQuestRepository.findByQuestParticipantId(participantId);
		return candidateRepeatQuests.stream().collect(Collectors.groupingBy(r -> r.getQuest().getType()));
	}

	private void createParticipatingQuestOfCandidate(Member candidate, LocalDateTime startDateTime, Map<QuestType, List<RepeatQuest>> groupedRepeatQuests) {
		List<AssignQuest> participatingQuests = new ArrayList<>();
		participatingQuests.addAll(createParticipatingQuest(candidate, DAILY, startDateTime, groupedRepeatQuests.getOrDefault(DAILY, new ArrayList<>())));
		participatingQuests.addAll(createParticipatingQuest(candidate, WEEKLY, startDateTime, groupedRepeatQuests.getOrDefault(WEEKLY, new ArrayList<>())));
		participatingQuests.addAll(createParticipatingQuest(candidate, MONTHLY, startDateTime, groupedRepeatQuests.getOrDefault(MONTHLY, new ArrayList<>())));
		assignQuestRepository.saveAll(participatingQuests);
	}

	@NotNull
	private List<AssignQuest> createParticipatingQuest(Member candidate, QuestType questType, LocalDateTime startDateTime, List<RepeatQuest> repeatQuests) {
		ParticipantId participantId = ParticipantId.of(candidate.getId().getId());
		List<AssignQuest> participatingQuests = new ArrayList<>();
		DisplayOrder displayOrder = DisplayOrder.of(1);

		for (RepeatQuest repeatQuest : repeatQuests) {
			AssignQuest assignQuest = repeatQuest.createAssignQuest(displayOrder, startDateTime);
			participatingQuests.add(assignQuest);
			displayOrder = displayOrder.increase(1);
		}

		int questThreshold = findQuestThreshold(candidate, questType);
		if (participatingQuests.size() < questThreshold) {
			int remainCount = questThreshold - participatingQuests.size();

			for (int i = 0; i < remainCount; i++) {
				AssignQuest assignQuest = createBlankAssignQuest(participantId, questType, displayOrder);
				participatingQuests.add(assignQuest);
				displayOrder = displayOrder.increase(1);
			}
		}

		return participatingQuests;
	}

	private int findQuestThreshold(Member candidate, QuestType questType) {
		return switch (questType) {
			case DAILY -> candidate.getQuestProperty().getDailyThreshold().getThreshold();
			case WEEKLY -> candidate.getQuestProperty().getWeeklyThreshold().getThreshold();
			case MONTHLY -> candidate.getQuestProperty().getMonthlyThreshold().getThreshold();
		};
	}

	// TODO <jhl221123>: 추후 AI 모델과 연동해 사용자 맞춤 퀘스트 생성하도록 수정이 필요합니다.
	@NotNull
	private AssignQuest createBlankAssignQuest(ParticipantId participantId, QuestType questType, DisplayOrder displayOrder) {
		return AssignQuest.of(
			AssignQuestId.of(UUIDGenerator.generate()),
			Quest.of(
				participantId,
				SubjectId.of(UUID.fromString(BLANK_SUBJECT_ID)),
				SubjectName.of(BLANK_SUBJECT_NAME),
				questType,
				QuestContent.of(BLANK_SUBJECT_CONTENT)
			),
			false,
			displayOrder,
			LocalDateTime.now()
		);
	}
}
