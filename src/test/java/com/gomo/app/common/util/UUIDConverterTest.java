package com.gomo.app.common.util;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Common unit]: UUID 변환 테스트")
class UUIDConverterTest {

	@DisplayName("UUID와 byte 배열 간의 양방향 변환이 정확해야 한다.")
	@Test
	void convert_UUID_to_byte() {
		UUID originalUuid = UUID.randomUUID();

		byte[] bytes = UUIDConverter.uuidToBytes(originalUuid);
		UUID convertedUuid = UUIDConverter.bytesToUuid(bytes);

		assertThat(originalUuid).isEqualTo(convertedUuid);
	}
}
