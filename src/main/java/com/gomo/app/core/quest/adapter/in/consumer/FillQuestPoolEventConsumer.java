package com.gomo.app.core.quest.adapter.in.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.gomo.app.common.arch.EventConsumer;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.core.quest.application.port.command.CreateQuestPoolCommand;
import com.gomo.app.core.quest.application.port.in.QuestPoolCreator;
import com.gomo.app.core.quest.domain.event.CreateQuestPoolEvent;
import com.gomo.app.support.messagebroker.application.port.in.IdempotentDirectEventConsumer;
import com.gomo.app.support.messagebroker.domain.model.DirectEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EventConsumer
public class FillQuestPoolEventConsumer {

	private final QuestPoolCreator questPoolCreator;

	@IdempotentDirectEventConsumer
	@RabbitListener(queues = "event.quest.pool.fill")
	public void handleEvent(DirectEvent directEvent) {
		CreateQuestPoolEvent event = JsonParser.fromJson(directEvent.getPayload(), CreateQuestPoolEvent.class);
		CreateQuestPoolCommand createQuestPoolCommand = CreateQuestPoolCommand.of(
			event.getParticipantId(),
			event.getSubjects().stream().map(subject -> CreateQuestPoolCommand.Subject.of(subject.getId(), subject.getName(), subject.getLevel())).toList(),
			event.getQuestType(),
			(int)event.getLimit()
		);
		questPoolCreator.create(createQuestPoolCommand);
	}
}
