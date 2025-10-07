package com.gomo.app.core.interest.presentation.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.interest.application.port.AdjustProficiencyPortIn;
import com.gomo.app.core.quest.event.CompleteQuestEvent;
import com.gomo.app.support.event.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.event.domain.model.EventEntry;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@EventConsumer
public class CompleteQuestEventConsumer {

	private final ProcessEventEntryPortIn processEventEntryPortIn;
	private final AdjustProficiencyPortIn adjustProficiencyPortIn;

	// todo jhl221123: try - catch 대신 AOP를 활용해 로깅 처리
	@RabbitListener(queues = "event.quest.completed.score")
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
			adjustProficiencyPortIn.adjust(event.getSubjectId(), event.getScoreReward());
		} catch (Exception e) {
			log.error("[{}] Failed to process event id={}, error={}", consumerName, eventEntryId, e.getMessage());
			throw e;
		}
	}
}
