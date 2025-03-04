package com.gomo.app.common.unit;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.util.UUIDGenerator;

@DisplayName("[Domain service]: uuid 생성기 테스트")
public class UUIDGeneratorTest {

	@DisplayName("uuid 생성기를 생성한다.")
	@Test
	void create_uuid_generator() {
		UUIDGenerator uuidGenerator = new UUIDGenerator();

		assertThat(uuidGenerator).isNotNull();
	}

	@DisplayName("무작위 uuid를 생성한다.")
	@Test
	void create_uuid() {
		UUID uuid = UUIDGenerator.generate();

		assertThat(uuid).isNotNull();
	}
}
