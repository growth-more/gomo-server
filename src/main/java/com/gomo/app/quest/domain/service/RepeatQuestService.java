package com.gomo.app.quest.domain.service;

import static com.gomo.app.quest.exception.RepeatQuestErrorCode.*;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.exception.RepeatQuestThresholdExceededException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class RepeatQuestService {

	private final MemberRepository memberRepository;
	private final RepeatQuestRepository repeatQuestRepository;

	public RepeatQuest create(ParticipantId participantId, Quest quest) {
		int repeatQuestCount = (int)repeatQuestRepository.countByQuestParticipantIdAndQuestType(participantId, quest.getType());
		ensureNotExceedQuestThreshold(participantId, quest.getType(), repeatQuestCount);

		int displayOrder = repeatQuestRepository.findMaxDisplayOrderByQuestType(participantId, quest.getType()) + 1;
		return repeatQuestRepository.save(createRepeatQuest(quest, displayOrder));
	}

	private void ensureNotExceedQuestThreshold(ParticipantId participantId, QuestType questType, int currentCount) {
		Member member = memberRepository.findById(MemberId.of(participantId.getId()))
			.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		if(member.hasReachedQuestThreshold(questType.name(), currentCount)) {
			throw new RepeatQuestThresholdExceededException(THRESHOLD_EXCEEDED);
		}
	}

	@NotNull
	private RepeatQuest createRepeatQuest(Quest quest, int displayOrder) {
		return RepeatQuest.of(
			RepeatQuestId.of(UUIDGenerator.generate()),
			quest,
			DisplayOrder.of(displayOrder)
		);
	}
}
