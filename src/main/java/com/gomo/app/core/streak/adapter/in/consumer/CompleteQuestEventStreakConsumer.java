package com.gomo.app.core.streak.adapter.in.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.core.streak.application.port.in.StreakCreator;
import com.gomo.app.support.evententry.application.port.IdempotentEventEntryConsumer;
import com.gomo.app.support.evententry.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventStreakConsumer {

	private final StreakCreator streakCreator;

	@IdempotentEventEntryConsumer
	@RabbitListener(queues = "event.quest.assign.complete.streak")
	public void handleEvent(EventEntry eventEntry) {
		CompleteQuestEvent event = JsonParser.fromJson(eventEntry.getPayload(), CompleteQuestEvent.class);
		streakCreator.create(event.getParticipantId(), event.getQuestType(), event.getQuestCompletionTime().toLocalDate());
	}
}
