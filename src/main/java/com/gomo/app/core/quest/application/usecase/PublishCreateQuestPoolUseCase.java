package com.gomo.app.core.quest.application.usecase;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.event.EventRouter;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.common.util.TimestampGenerator;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.application.port.PublishCreateQuestPoolPortIn;
import com.gomo.app.core.quest.application.port.ReadSubjectPortOut;
import com.gomo.app.core.quest.application.port.command.PublishCreateQuestPoolCommand;
import com.gomo.app.core.quest.application.port.dto.SubjectDto;
import com.gomo.app.core.quest.domain.model.participant.Participant;
import com.gomo.app.core.quest.event.CreateQuestPoolEvent;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.support.messagebroker.application.port.PublishMessagePortIn;
import com.gomo.app.support.messagebroker.domain.model.DirectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
class PublishCreateQuestPoolUseCase implements PublishCreateQuestPoolPortIn {

	private final ReadSubjectPortOut readSubjectPortOut;
	private final EventRouter eventRouter;
	private final PublishMessagePortIn publishMessagePortIn;

	@AuditLog(action = "PUBLISH_CREATE_QUEST_POOL_EVENT")
	@Override
	public void publish(PublishCreateQuestPoolCommand command) {
		List<Participant> participants = command.participants();
		Set<UUID> participantIds = participants.stream()
			.map(Participant::getId)
			.collect(Collectors.toSet());

		Map<UUID, List<SubjectDto>> interestsByMemberId = readSubjectPortOut.findAllByParticipantIds(participantIds).stream()
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
			publishMessagePortIn.send(exchange, routingKey, JsonParser.toJson(directEvent));
		}
	}
}
