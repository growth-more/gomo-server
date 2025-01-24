package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.OrderChanger;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateAssignQuestUseCase {

	private AssignQuestRepository assignQuestRepository;
	private OrderChanger orderChanger;

	public void update(MemberId memberId, QuestType questType, OrderUpdateAssignQuestRequest request) {

	}
}
