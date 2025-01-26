package com.gomo.app.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.exception.PolicyViolationException;
import com.gomo.app.interest.domain.model.Level;

@DisplayName("[Domain unit]: 레벨 생성 및 증가 테스트")
public class LevelTest {

	private static final int MAXIMUM_LEVEL = 100;
	private static final int NEGATIVE_LEVEL = -1;

	@DisplayName("기본 레벨은 0이다.")
	@Test
	void create_level() {
		Level level = Level.createDefault();

		assertThat(level.getLevel()).isEqualTo(0);
	}

	@DisplayName("레벨은 음수일 수 없다.")
	@Test
	void create_negative_level() {
		assertThatThrownBy(() -> Level.of(NEGATIVE_LEVEL))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Level must be positive or zero.");
	}

	@DisplayName("레벨은 서비스 정책에 따른 최대값 보다 클 수 없다.")
	@Test
	void create_greater_than_maximum_level() {
		assertThatThrownBy(() -> Level.of(MAXIMUM_LEVEL + 1))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Level cannot exceed the maximum level.");
	}

	@DisplayName("레벨이 증가한다.")
	@Test
	void increase_level() {
		Level level = Level.createDefault();
		Level increasedLevel = level.increase(1);

		assertThat(increasedLevel.getLevel()).isEqualTo(1);
	}

	@DisplayName("레벨 증가량은 0일 수 없다.")
	@Test
	void increase_level_with_zero_increment() {
		Level level = Level.createDefault();

		assertThatThrownBy(() -> level.increase(0))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Level increment must be positive.");
	}

	@DisplayName("레벨 증가량은 음수일 수 없다.")
	@Test
	void increase_level_with_negative_increment() {
		Level level = Level.createDefault();

		assertThatThrownBy(() -> level.increase(NEGATIVE_LEVEL))
			.isInstanceOf(PolicyViolationException.class)
			.hasMessageContaining("Level increment must be positive.");
	}

	@DisplayName("레벨이 서비스 정책에 따른 최대값에 도달했다면, 더 이상 증가하지 않는다.")
	@Test
	void increase_level_exceed_max_level() {
		Level level = Level.createDefault();
		Level increasedLevel = level.increase(MAXIMUM_LEVEL + 1);

		assertThat(increasedLevel.getLevel()).isEqualTo(MAXIMUM_LEVEL);
	}
}
