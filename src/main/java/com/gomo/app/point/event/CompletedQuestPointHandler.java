package com.gomo.app.point.event;

import static com.gomo.app.point.domain.model.SourceType.*;
import static com.gomo.app.point.domain.model.TransactionType.*;

import org.springframework.context.event.EventListener;

import com.gomo.app.common.event.EventHandler;
import com.gomo.app.point.domain.model.TransactorId;
import com.gomo.app.point.domain.service.PointService;
import com.gomo.app.quest.domain.model.PointReward;
import com.gomo.app.quest.event.PointQuestCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EventHandler
public class CompletedQuestPointHandler {

	private final PointService pointService;

	@EventListener(PointQuestCompletedEvent.class)
	public void handle(PointQuestCompletedEvent event) {
		PointReward pointReward = event.getPointReward();
		pointService.create(TransactorId.of(event.getParticipantId().getId()), QUEST, GAIN, pointReward.getAmount());

		log.info("[CompletedQuestPointHandler] point trace id: {}", pointReward.getTraceId());
	}
}
