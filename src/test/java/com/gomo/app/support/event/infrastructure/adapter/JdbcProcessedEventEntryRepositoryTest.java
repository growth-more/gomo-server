package com.gomo.app.support.event.infrastructure.adapter;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;

@DisplayName("[Infrastructure Integration]: Event Entry 처리 테스트")
class JdbcProcessedEventEntryRepositoryTest extends IntegrationTestBase {

	@Autowired
	private JdbcProcessedEventEntryRepository sut;

	@Test
	@DisplayName("processed event entry 저장 후, 해당 데이터가 존재하는지 확인한다.")
	void save_and_exists() {
		String eventEntryId = "123";
		String consumerName = "ConsumerClassName";

		sut.save(eventEntryId, consumerName);

		boolean exists = sut.existsByEventEntryIdAndConsumerName(eventEntryId, consumerName);
		assertThat(exists).isTrue();
	}

	@Test
	@DisplayName("저장되지 않은 event entry id와 consumer name 으로 조회할 수 없다.")
	void not_exists() {
		String eventEntryId = "123";
		String consumerName = "ConsumerClassName";

		boolean exists = sut.existsByEventEntryIdAndConsumerName(eventEntryId, consumerName);

		assertThat(exists).isFalse();
	}
}
