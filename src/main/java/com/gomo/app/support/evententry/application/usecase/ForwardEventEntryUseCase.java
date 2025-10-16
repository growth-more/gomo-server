package com.gomo.app.support.evententry.application.usecase;

import static com.gomo.app.common.event.EventStatus.*;

import java.util.List;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.event.EventRouter;
import com.gomo.app.common.event.EventStatus;
import com.gomo.app.common.util.JsonParser;
import com.gomo.app.support.evententry.application.port.ForwardEventEntryPortIn;
import com.gomo.app.support.evententry.domain.model.EventEntry;
import com.gomo.app.support.evententry.domain.repository.EventEntryRepository;
import com.gomo.app.support.messagebroker.application.port.PublishMessagePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ForwardEventEntryUseCase implements ForwardEventEntryPortIn {

	private final EventEntryRepository eventEntryRepository;
	private final EventRouter eventRouter;
	private final PublishMessagePortIn publishMessagePortIn;

	@Override
	public void execute(int size) {
		List<EventEntry> pendingEvents = eventEntryRepository.findByEventStatus(PENDING.name(), size);
		for (EventEntry entry : pendingEvents) {
			String eventName = entry.getEventName();
			String exchange = eventRouter.getExchange(eventName);
			String routingKey = eventRouter.getRoutingKey(eventName);
			publishMessagePortIn.send(exchange, routingKey, JsonParser.toJson(entry));
			entry.update(EventStatus.PROCESSED);
		}
	}
}
