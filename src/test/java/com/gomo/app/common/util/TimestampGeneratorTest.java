package com.gomo.app.common.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Common unit]: 타임스탬프 생성기 테스트")
public class TimestampGeneratorTest {

	@DisplayName("타임스탬프 생성기를 생성한다.")
	@Test
	void create_timestamp_generator() {
		TimestampGenerator timestampGenerator = new TimestampGenerator();

		assertThat(timestampGenerator).isNotNull();
	}

	@DisplayName("타임스탬프를 생성한다.")
	@Test
	void create_timestamp() {
		long timestamp = TimestampGenerator.generate();

		assertThat(timestamp).isGreaterThan(0);
	}
}
