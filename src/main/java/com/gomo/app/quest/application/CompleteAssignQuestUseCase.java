package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.AssignQuestId;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.domain.service.QuestRewardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CompleteAssignQuestUseCase {

	private QuestRewardService questRewardService;
	private AssignQuestRepository assignQuestRepository;

	public void complete(MemberId memberId, AssignQuestId assignQuestId, String proofUrl) {

	}
}
