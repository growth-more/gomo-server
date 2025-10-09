package com.gomo.app.support.evententry.domain.repository;

public interface ProcessedEventEntryRepository {

	void save(String eventEntryId, String consumerName);

	boolean existsByEventEntryIdAndConsumerName(String eventEntryId, String consumerName);
}
