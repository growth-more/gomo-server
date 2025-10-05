package com.gomo.app.support.event.application.usecase;

import static com.gomo.app.common.event.EventStatus.*;

import java.util.List;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.event.EventStatus;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.support.event.application.port.ForwardEventEntryPortIn;
import com.gomo.app.support.event.application.port.MessageBrokerPortOut;
import com.gomo.app.support.event.domain.model.EventEntry;
import com.gomo.app.support.event.domain.repository.EventEntryRepository;
import com.gomo.app.support.event.infrastructure.EventRouter;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
class ForwardEventEntryUseCase implements ForwardEventEntryPortIn {

	private final EventEntryRepository eventEntryRepository;
	private final EventRouter eventRouter;
	private final MessageBrokerPortOut messageBrokerPortOut;

	@Override
	public void execute(int size) {
		List<EventEntry> pendingEvents = eventEntryRepository.findByEventStatus(PENDING.name(), size);
		for (EventEntry entry : pendingEvents) {
			String eventName = entry.getEventName();
			String exchange = eventRouter.getExchange(eventName);
			String routingKey = eventRouter.getRoutingKey(eventName);
			String eventJson = JsonParser.toJson(entry);
			messageBrokerPortOut.send(exchange, routingKey, eventJson);
			entry.update(EventStatus.COMPLETED);
		}
	}
}
