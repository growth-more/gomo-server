package com.gomo.app.quest.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.domain.service.OrderChanger;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class OrderUpdateRepeatQuestUseCase {

	private final RepeatQuestRepository repeatQuestRepository;
	private final OrderChanger orderChanger;

	public void update(UUID accessorId, OrderUpdateRepeatQuestRequest request) {

	}
}
