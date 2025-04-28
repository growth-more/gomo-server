package com.gomo.app.quest.domain.service;

import org.jetbrains.annotations.NotNull;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.Quest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.model.RepeatQuestId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.exception.RepeatQuestNotFoundException;
import com.gomo.app.quest.exception.RepeatQuestThresholdExceededException;
import com.gomo.app.quest.exception.code.RepeatQuestErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class RepeatQuestService {

	private final MemberService memberService;
	private final RepeatQuestRepository repeatQuestRepository;

	public RepeatQuest create(ParticipantId participantId, Quest quest) {
		// TODO <jhl221123>: 생성 자체와 관련 없는 로직은 응용 영역의 책임에 가깝기 때문에 이동이 필요합니다.
		int repeatQuestCount = (int)repeatQuestRepository.countByQuestParticipantIdAndQuestType(participantId, quest.getType());
		ensureNotExceedQuestThreshold(participantId, quest.getType(), repeatQuestCount);

		int displayOrder = repeatQuestRepository.findMaxDisplayOrderByQuestType(participantId, quest.getType()) + 1;
		return repeatQuestRepository.save(createRepeatQuest(quest, displayOrder));
	}

	public RepeatQuest find(RepeatQuestId repeatQuestId) {
		return repeatQuestRepository.findById(repeatQuestId)
			.orElseThrow(() -> new RepeatQuestNotFoundException(RepeatQuestErrorCode.NOT_FOUND));
	}

	private void ensureNotExceedQuestThreshold(ParticipantId participantId, QuestType questType, int currentCount) {
		// TODO <jhl221123>: 응용 서비스에서 회원 조회 -> Participant 전환 -> 퀘스트 도메인 영역으로 전달 하도록 수정이 필요합니다.
		Member member = memberService.find(MemberId.of(participantId.getId()));
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
