package com.gomo.app.core.interest.unit.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.interest.domain.model.Level;
import com.gomo.app.core.interest.exception.LevelConstraintViolationException;
import com.gomo.app.core.interest.exception.code.LevelErrorCode;

@DisplayName("[Domain unit]: 레벨 생성 및 증가 테스트")
public class LevelTest {

	private static final int MAXIMUM_LEVEL = 100;
	private static final int NEGATIVE = -1;

	@DisplayName("기본 레벨은 0이다.")
	@Test
	void create_level() {
		Level level = Level.createDefault();

		assertThat(level.getLevel()).isEqualTo(0);
	}

	@DisplayName("레벨은 음수일 수 없다.")
	@Test
	void create_negative_level() {
		assertThatThrownBy(() -> Level.of(NEGATIVE, NEGATIVE))
			.isInstanceOf(LevelConstraintViolationException.class)
			.hasMessageContaining(LevelErrorCode.NEGATIVE.getMessage());
	}

	@DisplayName("레벨은 서비스 정책에 따른 최대값 보다 클 수 없다.")
	@Test
	void create_greater_than_maximum_level() {
		assertThatThrownBy(() -> Level.of(MAXIMUM_LEVEL + 1, NEGATIVE))
			.isInstanceOf(LevelConstraintViolationException.class)
			.hasMessageContaining(LevelErrorCode.EXCEED_MAXIMUM.getMessage());
	}
}
