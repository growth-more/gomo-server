package com.gomo.app.point.event;

import static com.gomo.app.point.domain.model.SourceType.*;
import static com.gomo.app.point.domain.model.TransactionType.*;

import java.util.UUID;

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
		UUID participantId = event.getParticipantId().getId();
		PointReward pointReward = event.getPointReward();
		pointService.create(TransactorId.of(participantId), QUEST, GAIN, pointReward.getAmount());

		log.info("[CompletedQuestPointHandler] Processing PointQuestCompletedEvent with member id: {}, trace id: {}", participantId, pointReward.getTraceId());
	}
}
