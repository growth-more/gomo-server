package com.gomo.app.common.unit;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.domain.service.OrderChangeable;
import com.gomo.app.common.domain.service.OrderChanger;

import lombok.Getter;

@DisplayName("[Domain service]: 정렬 순서 변경 테스트")
public class OrderChangerTest {

	@DisplayName("정렬 순서를 변경한다.")
	@Test
	void change_display_order() {
		List<OrderChangeable> candidates = List.of(MockOrderChangeable.of(1), MockOrderChangeable.of(2), MockOrderChangeable.of(3));
		List<DisplayOrder> changedOrders = List.of(DisplayOrder.of(3), DisplayOrder.of(2), DisplayOrder.of(1));

		OrderChanger.change(candidates, changedOrders);

		assertThat(candidates)
			.extracting(candidate -> ((MockOrderChangeable)candidate).getDisplayOrder())
			.containsExactly(DisplayOrder.of(3), DisplayOrder.of(2), DisplayOrder.of(1));
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
