package com.gomo.app.quest.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.OrderChanger;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;
	private final OrderChanger orderChanger;

	public void update(MemberId memberId, OrderUpdateRepeatQuestRequest request) {

	}
}
