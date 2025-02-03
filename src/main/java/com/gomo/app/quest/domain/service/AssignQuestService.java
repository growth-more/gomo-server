package com.gomo.app.quest.domain.service;

import java.util.List;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.ParticipantId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class AssignQuestService {

	private final AssignQuestRepository assignQuestRepository;
	private final RepeatQuestRepository repeatQuestRepository;

	// TODO <jhl221123>: 매일 정해진 시간마다 모든 회원을 대상으로 퀘스트 타입별로 수행되어야 한다.
	public List<AssignQuest> createAssignQuests(ParticipantId participantId, QuestType questType, int questThreshold) {
		// 1. 참여자와 퀘스트 타입으로 반복 퀘스트 목록을 조회한다.

		// 2. 반복 퀘스트로 할당 퀘스트 목록을 생성한다.

		// 3. 설정 값과 비교해 남은 수만큼 할당 퀘스트를 추가 생성한다.

		// 4. 최종 생성된 할당 퀘스트 목록을 반환한다.
		return null;
	}
}
