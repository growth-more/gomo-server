package com.gomo.app.support.event.domain.repository;

public interface ProcessedEventEntryRepository {

	void save(String eventEntryId, String consumerName);

	boolean existsByEventEntryIdAndConsumerName(String eventEntryId, String consumerName);
}
