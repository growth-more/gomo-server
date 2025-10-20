package com.gomo.app.support.messagebroker.infrastructure.adapter;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.test.IntegrationTest;

@DisplayName("[Infrastructure Integration]: DirectEvent 처리 테스트")
@IntegrationTest
class JdbcProcessedDirectEventRepositoryTest {

	@Autowired
	private JdbcProcessedDirectEventRepository sut;

	@DisplayName("processed direct event 저장 후, 해당 데이터가 존재하는지 확인한다.")
	@Test
	void save_and_exists() {
		String directEventId = "123";
		String consumerName = "ConsumerClassName";

		sut.save(directEventId, consumerName);

		boolean exists = sut.existsByDirectEventIdAndConsumerName(directEventId, consumerName);
		assertThat(exists).isTrue();
	}

	@DisplayName("저장되지 않은 direct event id와 consumer name 으로 조회할 수 없다.")
	@Test
	void not_exists() {
		String directEventId = "123";
		String consumerName = "ConsumerClassName";

		boolean exists = sut.existsByDirectEventIdAndConsumerName(directEventId, consumerName);

		assertThat(exists).isFalse();
	}
}
