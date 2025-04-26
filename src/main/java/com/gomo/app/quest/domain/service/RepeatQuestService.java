package com.gomo.app.quest.domain.service;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.displayorder.DisplayOrder;
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
import com.gomo.app.quest.exception.code.RepeatQuestErrorCode;

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
		// TODO <jhl221123>: 여기서 회원을 조회하면 안 되고, 회원 서비스에서 공통 처리되어야 한다.
		Member member = memberRepository.findById(MemberId.of(participantId.getId()))
			.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		if(member.hasReachedQuestThreshold(questType.name(), currentCount)) {
			throw new RepeatQuestThresholdExceededException(RepeatQuestErrorCode.THRESHOLD_EXCEEDED);
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
