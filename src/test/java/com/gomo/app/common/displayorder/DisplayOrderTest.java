package com.gomo.app.common.displayorder;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Common unit]: 정렬 순서 생성 및 수정 테스트")
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
			.isInstanceOf(DisplayOrderConstraintViolationException.class)
			.hasMessageContaining(DisplayOrderErrorCode.NON_POSITIVE.getMessage());
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
			.isInstanceOf(DisplayOrderConstraintViolationException.class)
			.hasMessageContaining(DisplayOrderErrorCode.NON_POSITIVE_INCREMENT.getMessage());
	}

	@DisplayName("순서가 같다면 동일하게 취급한다.")
	@Test
	void same_display_order() {
		DisplayOrder displayOrder = DisplayOrder.of(1);

		assertThat(displayOrder).isEqualTo(displayOrder);
		assertThat(displayOrder).isEqualTo(DisplayOrder.of(1));
	}

	@DisplayName("순서가 다르면 다르게 취급한다.")
	@Test
	void not_same_display_order() {
		DisplayOrder displayOrder = DisplayOrder.of(1);

		assertThat(displayOrder).isNotEqualTo(DisplayOrder.of(2));
	}
}
