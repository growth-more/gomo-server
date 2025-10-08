package com.gomo.app.core.streak.presentation.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.core.streak.application.port.CreateStreakPortIn;
import com.gomo.app.support.event.application.port.IdempotentEventEntryConsumer;
import com.gomo.app.support.event.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventStreakConsumer {

	private final CreateStreakPortIn createStreakPortIn;

	@IdempotentEventEntryConsumer
	@RabbitListener(queues = "event.quest.complete.streak")
	public void handleEvent(EventEntry eventEntry) {
		CompleteQuestEvent event = JsonParser.fromJson(eventEntry.getPayload(), CompleteQuestEvent.class);
		createStreakPortIn.create(event.getParticipantId(), event.getQuestType(), event.getQuestCompletionTime().toLocalDate());
	}
}
