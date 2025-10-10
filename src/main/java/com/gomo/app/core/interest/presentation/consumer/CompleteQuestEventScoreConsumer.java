package com.gomo.app.core.interest.presentation.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.interest.application.port.AdjustProficiencyPortIn;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.evententry.application.port.IdempotentEventEntryConsumer;
import com.gomo.app.support.evententry.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventScoreConsumer {

	private final AdjustProficiencyPortIn adjustProficiencyPortIn;

	@IdempotentEventEntryConsumer
	@RabbitListener(queues = "event.quest.complete.score")
	public void handleEvent(EventEntry eventEntry) {
		CompleteQuestEvent event = JsonParser.fromJson(eventEntry.getPayload(), CompleteQuestEvent.class);
		adjustProficiencyPortIn.adjust(event.getSubjectId(), event.getScoreReward());
	}
}
