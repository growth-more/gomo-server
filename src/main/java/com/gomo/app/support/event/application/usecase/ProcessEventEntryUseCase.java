package com.gomo.app.support.event.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.event.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.event.domain.repository.ProcessedEventEntryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ProcessEventEntryUseCase implements ProcessEventEntryPortIn {

	private final ProcessedEventEntryRepository processedEventEntryRepository;

	@Override
	public void save(String eventEntryId, String consumerName) {
		processedEventEntryRepository.save(eventEntryId, consumerName);
	}

	@Override
	public boolean isAlreadyProcessed(String eventEntryId, String consumerName) {
		return processedEventEntryRepository.existsByEventEntryIdAndConsumerName(eventEntryId, consumerName);
	}
}
