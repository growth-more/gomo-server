package com.gomo.app.common.unit;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.displayorder.OrderChangeable;
import com.gomo.app.displayorder.OrderChanger;
import com.gomo.app.interest.presentation.request.UpdateOrderRequest;

import lombok.Getter;

@DisplayName("[Domain service]: 정렬 순서 변경 테스트")
public class OrderChangerTest {

	private static final UUID FIRST_CANDIDATE_ID = UUID.randomUUID();
	private static final UUID SECOND_CANDIDATE_ID = UUID.randomUUID();
	private static final UUID THIRD_CANDIDATE_ID = UUID.randomUUID();

	@DisplayName("정렬 순서를 변경한다.")
	@Test
	void change_display_order() {
		Map<UUID, OrderChangeable> candidates = getOrderChangeableMap();
		List<UpdateOrderRequest> changedOrders = getRequests();

		OrderChanger.change(candidates, changedOrders);

		assertThat(candidates.values())
			.extracting(candidate -> ((MockOrderChangeable)candidate).getDisplayOrder())
			.containsExactly(DisplayOrder.of(3), DisplayOrder.of(2), DisplayOrder.of(1));
	}

	@DisplayName("정렬 대상이 존재하지 않으면 변경에 실패한다.")
	@Test
	void change_display_order_not_same_size() {
		Map<UUID, OrderChangeable> candidates = getOrderChangeableMap();
		List<UpdateOrderRequest> changedOrders = getRequests();

		changedOrders.add(UpdateOrderRequest.of(UUID.randomUUID(), 4));

		assertThatThrownBy(() -> OrderChanger.change(candidates, changedOrders))
			.isInstanceOf(NullPointerException.class);
	}

	private static @NotNull Map<UUID, OrderChangeable> getOrderChangeableMap() {
		Map<UUID, OrderChangeable> map = new LinkedHashMap<>();
		map.put(FIRST_CANDIDATE_ID, MockOrderChangeable.of(1));
		map.put(SECOND_CANDIDATE_ID, MockOrderChangeable.of(2));
		map.put(THIRD_CANDIDATE_ID, MockOrderChangeable.of(3));
		return map;
	}

	private @NotNull List<UpdateOrderRequest> getRequests() {
		List<UpdateOrderRequest> requests = new ArrayList<>();
		requests.add(UpdateOrderRequest.of(SECOND_CANDIDATE_ID, 2));
		requests.add(UpdateOrderRequest.of(THIRD_CANDIDATE_ID, 1));
		requests.add(UpdateOrderRequest.of(FIRST_CANDIDATE_ID, 3));
		return requests;
	}

	@Getter
	static class MockOrderChangeable implements OrderChangeable {

		DisplayOrder displayOrder;

		private MockOrderChangeable(int order) {
			this.displayOrder = DisplayOrder.of(order);
		}

		public static MockOrderChangeable of(int order) {
			return new MockOrderChangeable(order);
		}

		@Override
		public void changeOrder(DisplayOrder displayOrder) {
			this.displayOrder = displayOrder;
		}
	}
}
