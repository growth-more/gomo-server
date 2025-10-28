package com.gomo.app.core.interest.adapter.in.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.interest.application.port.in.AdjustProficiencyUseCase;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.evententry.application.port.IdempotentEventEntryConsumer;
import com.gomo.app.support.evententry.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventScoreConsumer {

	private final AdjustProficiencyUseCase adjustProficiencyUseCase;

	@IdempotentEventEntryConsumer
	@RabbitListener(queues = "event.quest.assign.complete.score")
	public void handleEvent(EventEntry eventEntry) {
		CompleteQuestEvent event = JsonParser.fromJson(eventEntry.getPayload(), CompleteQuestEvent.class);
		adjustProficiencyUseCase.adjust(event.getSubjectId(), event.getScoreReward());
	}
}
