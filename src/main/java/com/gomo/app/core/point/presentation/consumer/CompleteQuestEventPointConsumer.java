package com.gomo.app.core.point.presentation.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.point.application.port.CreatePointPortIn;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.event.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.event.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventPointConsumer {

	private final ProcessEventEntryPortIn processEventEntryPortIn;
	private final CreatePointPortIn createPointPortIn;

	@RabbitListener(queues = "event.quest.complete.point")
	@Transactional(rollbackFor = Exception.class)
	public void handleEvent(EventEntry eventEntry) {
		String eventEntryId = String.valueOf(eventEntry.getId());
		String consumerName = this.getClass().getName();
		try {
			if (processEventEntryPortIn.isAlreadyProcessed(eventEntryId, consumerName)) {
				return;
			}
			processEventEntryPortIn.save(eventEntryId, consumerName);
			CompleteQuestEvent event = JsonParser.fromJson(eventEntry.getPayload(), CompleteQuestEvent.class);
			createPointPortIn.create(event.getParticipantId(), "QUEST", "GAIN", event.getPointReward());
		} catch (Exception e) {
			log.error("[{}] Failed to process event id={}, error={}", consumerName, eventEntryId, e.getMessage());
			throw e;
		}
	}
}
