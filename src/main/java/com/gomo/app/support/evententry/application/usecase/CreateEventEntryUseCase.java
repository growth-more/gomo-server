package com.gomo.app.support.evententry.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.evententry.application.port.CreateEventEntryPortIn;
import com.gomo.app.support.evententry.domain.model.EventEntry;
import com.gomo.app.support.evententry.domain.repository.EventEntryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class CreateEventEntryUseCase implements CreateEventEntryPortIn {

	private final EventEntryRepository eventEntryRepository;

	@Override
	public Long create(String eventName, String payload, long timestamp) {
		EventEntry eventEntry = eventEntryRepository.save(EventEntry.of(eventName, payload, timestamp));
		return eventEntry.getId();
	}
}
