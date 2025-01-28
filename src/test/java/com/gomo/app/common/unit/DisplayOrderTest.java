package com.gomo.app.common.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.common.exception.PolicyViolationException;

@DisplayName("[Domain unit]: 정렬 순서 생성 및 수정 테스트")
public class DisplayOrderTest {

	@DisplayName("정렬 순서를 생성한다.")
	@Test
	void create_display_order() {
		DisplayOrder displayOrder = DisplayOrder.of(1);

		assertThat(displayOrder.getDisplayOrder()).isEqualTo(1);
	}

	@DisplayName("정렬 순서는 음수일 수 없다.")
	@Test
	void create_display_order_with_negative() {
		assertThatThrownBy(() -> DisplayOrder.of(-1))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("DisplayOrder must be positive");
	}

	@DisplayName("정렬 순서를 증가시킨다.")
	@Test
	void increase_display_order() {
		DisplayOrder displayOrder = DisplayOrder.of(1);
		DisplayOrder increasedDisplayOrder = displayOrder.increase(1);

		assertThat(increasedDisplayOrder.getDisplayOrder()).isEqualTo(2);
	}

	@DisplayName("정렬 순서는 음수로 증가시킬 수 없다.")
	@Test
	void increase_display_order_with_negative() {
		DisplayOrder displayOrder = DisplayOrder.of(1);

		assertThatThrownBy(() -> displayOrder.increase(-1))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Increment must be positive");
	}
}
