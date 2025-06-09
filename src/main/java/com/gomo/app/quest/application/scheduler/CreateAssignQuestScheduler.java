package com.gomo.app.quest.application.scheduler;

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
import org.springframework.stereotype.Component;

import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.application.translator.ParticipantTranslator;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.model.Participant;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestContent;
import com.gomo.app.quest.domain.model.QuestQuota;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.SubjectId;
import com.gomo.app.quest.domain.model.SubjectName;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CreateAssignQuestScheduler {

	private static final String BLANK_SUBJECT_ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";
	private static final String BLANK_SUBJECT_NAME = "BLANK";
	private static final String BLANK_SUBJECT_CONTENT = "BLANK";

	private final MemberRepository memberRepository;
	private final AssignQuestRepository assignQuestRepository;
	private final RepeatQuestRepository repeatQuestRepository;

	@Scheduled(cron = "0 0 2 * * ?")
	public void createParticipatingQuestOfAllActiveMember() {
		LocalDateTime startDateTime = LocalDateTime.now();
		List<Participant> participants = findActiveParticipant(startDateTime.toLocalDate());

		for (Participant participant : participants) {
			ParticipantId participantId = ParticipantId.of(participant.getId().getId());
			Map<QuestType, List<RepeatQuest>> groupedRepeatQuests = buildRepeatQuestGroupByQuestType(participantId);
			createParticipatingQuestOfCandidate(participant, startDateTime, groupedRepeatQuests);
		}
	}

	private List<Participant> findActiveParticipant(LocalDate startDate) {
		return memberRepository.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
				ActivateStatus.ACTIVE,
				startDate.minusDays(1).atStartOfDay()
			)
			.stream()
			.map(ParticipantTranslator::from)
			.collect(Collectors.toList());
	}

	@NotNull
	private Map<QuestType, List<RepeatQuest>> buildRepeatQuestGroupByQuestType(ParticipantId participantId) {
		List<RepeatQuest> candidateRepeatQuests = repeatQuestRepository.findByQuestParticipantId(participantId);
		return candidateRepeatQuests.stream().collect(Collectors.groupingBy(r -> r.getQuest().getType()));
	}

	private void createParticipatingQuestOfCandidate(Participant participant, LocalDateTime startDateTime, Map<QuestType, List<RepeatQuest>> groupedRepeatQuests) {
		List<AssignQuest> participatingQuests = new ArrayList<>();
		participatingQuests.addAll(createParticipatingQuest(participant, DAILY, startDateTime, groupedRepeatQuests.getOrDefault(DAILY, new ArrayList<>())));
		participatingQuests.addAll(createParticipatingQuest(participant, WEEKLY, startDateTime, groupedRepeatQuests.getOrDefault(WEEKLY, new ArrayList<>())));
		participatingQuests.addAll(createParticipatingQuest(participant, MONTHLY, startDateTime, groupedRepeatQuests.getOrDefault(MONTHLY, new ArrayList<>())));
		assignQuestRepository.saveAll(participatingQuests);
	}

	@NotNull
	private List<AssignQuest> createParticipatingQuest(Participant participant, QuestType questType, LocalDateTime startDateTime, List<RepeatQuest> repeatQuests) {
		ParticipantId participantId = ParticipantId.of(participant.getId().getId());
		List<AssignQuest> participatingQuests = new ArrayList<>();
		DisplayOrder displayOrder = DisplayOrder.of(1);

		for (RepeatQuest repeatQuest : repeatQuests) {
			AssignQuest assignQuest = repeatQuest.createAssignQuest(displayOrder, startDateTime);
			participatingQuests.add(assignQuest);
			displayOrder = displayOrder.increase(1);
		}

		QuestQuota questQuota = participant.getQuestQuota();
		while(!questQuota.isExceeded(questType, participatingQuests.size())) {
			AssignQuest assignQuest = createBlankAssignQuest(participantId, questType, displayOrder);
			participatingQuests.add(assignQuest);
			displayOrder = displayOrder.increase(1);
		}

		return participatingQuests;
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
