package com.gomo.app.core.quest.application.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.event.CreateQuestPoolEvent;
import com.gomo.app.common.event.EventRouter;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.application.port.command.PublishCreateQuestPoolCommand;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;
import com.gomo.app.core.quest.application.port.in.QuestPoolEventPublisher;
import com.gomo.app.core.quest.application.port.out.SubjectReader;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.support.messagebroker.application.port.in.MessagePublisher;
import com.gomo.app.support.messagebroker.domain.model.DirectEvent;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class QuestPoolEventService implements QuestPoolEventPublisher {

	private final SubjectReader subjectReader;
	private final EventRouter eventRouter;
	private final MessagePublisher messagePublisher;

	@Override
	@AuditLog(action = "QUEST_POOL_CREATE_EVENT_PUBLISH")
	public void publish(PublishCreateQuestPoolCommand command) {
		List<Participant> participants = command.participants();
		Set<UUID> participantIds = participants.stream()
			.map(Participant::getId)
			.collect(Collectors.toSet());

		Map<UUID, List<SubjectDto>> interestsByMemberId = subjectReader.readAllByParticipantIds(participantIds).stream()
			.collect(Collectors.groupingBy(SubjectDto::participantId));
		for (Participant participant : participants) {
			UUID participantId = participant.getId();
			List<CreateQuestPoolEvent.Subject> subjects = interestsByMemberId.getOrDefault(participantId, List.of()).stream()
				.map(subjectDto -> CreateQuestPoolEvent.Subject.of(
					subjectDto.id(),
					subjectDto.name(),
					subjectDto.level()))
				.toList();

			CreateQuestPoolEvent event = CreateQuestPoolEvent.of(
				participantId, subjects, command.questType(), command.limitPerParticipant(), TimestampGenerator.generate());
			String eventName = event.getClass().getSimpleName();
			String exchange = eventRouter.getExchange(eventName);
			String routingKey = eventRouter.getRoutingKey(eventName);
			DirectEvent directEvent = DirectEvent.of(UUIDGenerator.generate(), eventName, JsonParser.toJson(event));
			messagePublisher.send(exchange, routingKey, JsonParser.toJson(directEvent));
		}
	}
}
