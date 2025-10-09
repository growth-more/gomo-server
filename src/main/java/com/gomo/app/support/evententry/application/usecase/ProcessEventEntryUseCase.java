package com.gomo.app.support.evententry.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.evententry.application.port.ProcessEventEntryPortIn;
import com.gomo.app.support.evententry.domain.repository.ProcessedEventEntryRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ProcessEventEntryUseCase implements ProcessEventEntryPortIn {

	private final ProcessedEventEntryRepository processedEventEntryRepository;

	@Override
	public void process(String eventEntryId, String consumerName) {
		processedEventEntryRepository.save(eventEntryId, consumerName);
	}

	@Override
	public boolean isAlreadyProcessed(String eventEntryId, String consumerName) {
		return processedEventEntryRepository.existsByEventEntryIdAndConsumerName(eventEntryId, consumerName);
	}
}
