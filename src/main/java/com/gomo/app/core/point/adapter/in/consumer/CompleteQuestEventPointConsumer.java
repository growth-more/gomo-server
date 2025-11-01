package com.gomo.app.core.point.adapter.in.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.point.application.port.in.PointCreator;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.evententry.application.port.IdempotentEventEntryConsumer;
import com.gomo.app.support.evententry.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventPointConsumer {

	private final PointCreator pointCreator;

	@IdempotentEventEntryConsumer
	@RabbitListener(queues = "event.quest.assign.complete.point")
	public void handleEvent(EventEntry eventEntry) {
		CompleteQuestEvent event = JsonParser.fromJson(eventEntry.getPayload(), CompleteQuestEvent.class);
		pointCreator.create(event.getParticipantId(), "QUEST", "GAIN", event.getPointReward());
	}
}
