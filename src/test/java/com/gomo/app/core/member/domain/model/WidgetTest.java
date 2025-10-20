package com.gomo.app.core.member.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Domain unit]: 위젯 테스트")
class WidgetTest {

	@DisplayName("기본 스냅샷으로 위젯을 생성한다.")
	@Test
	void create_default_widget() {
		Widget widget = Widget.createDefault();
		assertThat(widget.getSnapshot()).isNotNull();
	}

	@DisplayName("위젯 스냅샷을 수정한다.")
	@Test
	void update_widget_snapshot() {
		Widget widget = Widget.createDefault();
		String updatedSnapshot = "{ \"test\": \"test_snapshot\" }";
		
		Widget actual = widget.update(updatedSnapshot);

		assertThat(actual.getSnapshot()).isEqualTo(updatedSnapshot);
	}
}
