package com.gomo.app.support.messagebroker.domain.repository;

public interface ProcessedDirectEventRepository {

	void save(String directEventId, String consumerName);

	boolean existsByDirectEventIdAndConsumerName(String directEventId, String consumerName);
}
