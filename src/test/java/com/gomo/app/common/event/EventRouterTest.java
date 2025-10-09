package com.gomo.app.common.event;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.test.IntegrationTest;

@DisplayName("[Common integration] 이벤트 라우터 테스트")
@IntegrationTest
class EventRouterTest {

	@Autowired
	private EventRouter eventRouter;

	@DisplayName("exchange 정보를 조회한다.")
	@Test
	void get_exchange_with_routing_event() {
		String exchange = eventRouter.getExchange("StubRoutingEvent");
		assertThat(exchange).isEqualTo("test-exchange");
	}

	@DisplayName("routing key 정보를 조회한다.")
	@Test
	void get_routing_key_with_routing_event() {
		String routingKey = eventRouter.getRoutingKey("StubRoutingEvent");
		assertThat(routingKey).isEqualTo("test.success");
	}

	@DisplayName("라우팅 정보가 없다면, exchange 정보를 조회할 수 없다.")
	@Test
	void get_exchange_with_no_routing_event() {
		assertThrows(IllegalStateException.class, () -> eventRouter.getExchange("StubNoRoutingEvent"));
	}

	@DisplayName("라우팅 정보가 없다면, exchange 정보는 조회할 수 없다.")
	@Test
	void get_routing_key_with_no_routing_event() {
		assertThrows(IllegalStateException.class, () -> eventRouter.getRoutingKey("StubNoRoutingEvent"));
	}

	@DisplayName("존재하지 않는 이벤트의 routing key 정보는 조회할 수 없다.")
	@Test
	void get_exchange_with_nonexistent_event() {
		assertThrows(IllegalStateException.class, () -> eventRouter.getExchange("NonExistentEvent"));
	}

	@DisplayName("존재하지 않는 이벤트의 routing key 정보는 조회할 수 없다.")
	@Test
	void get_routing_key_with_nonexistent_event() {
		assertThrows(IllegalStateException.class, () -> eventRouter.getRoutingKey("NonExistentEvent"));
	}

	@EventRouting(exchange = "test-exchange", routingKey = "test.success")
	static class StubRoutingEvent extends Event {
	}

	static class StubNoRoutingEvent extends Event {
	}
}
