package com.gomo.app.member.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.member.domain.model.Motto;
import com.gomo.app.core.member.exception.MottoConstraintViolationException;
import com.gomo.app.core.member.exception.code.MottoErrorCode;

@DisplayName("[Domain unit]: 모토 생성 및 수정 테스트")
public class MottoTest {

	private static final String MOTTO = "test";
	private static final String TOO_LONG_MOTTO = Stream.generate(() -> "test").limit(200).collect(Collectors.joining());
	private static final String FORBIDDEN_MOTTO = "[<>&';|{}[]()`]--*";

	@DisplayName("모토를 생성한다.")
	@Test
	void create_motto() {
		Motto motto = Motto.of(MOTTO);
		assertThat(motto.toString()).isEqualTo(MOTTO);
	}

	@DisplayName("최대 길이보다 긴 모토는 생성할 수 없다.")
	@Test
	void create_motto_with_long_length() {
		assertThatThrownBy(() -> Motto.of(TOO_LONG_MOTTO))
			.isInstanceOf(MottoConstraintViolationException.class)
			.hasMessageContaining(MottoErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("금지 문자가 포함된 모토는 생성할 수 없다.")
	@Test
	void create_motto_with_forbidden_characters() {
		assertThatThrownBy(() -> Motto.of(FORBIDDEN_MOTTO))
			.isInstanceOf(MottoConstraintViolationException.class)
			.hasMessageContaining(MottoErrorCode.FORBIDDEN.getMessage());
	}

	@DisplayName("모토를 수정한다.")
	@Test
	void update_motto() {
		Motto motto = Motto.of(MOTTO);
		Motto updatedMotto = motto.update("updated_motto");

		assertThat(updatedMotto.toString()).isEqualTo("updated_motto");
	}

	@DisplayName("최대 길이보다 긴 모토로 수정할 수 없다.")
	@Test
	void update_motto_with_long_length() {
		Motto motto = Motto.of(MOTTO);

		assertThatThrownBy(() -> motto.update(TOO_LONG_MOTTO))
			.isInstanceOf(MottoConstraintViolationException.class)
			.hasMessageContaining(MottoErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("금지 문자를 포함한 모토로 수정할 수 없다,")
	@Test
	void update_motto_with_forbidden_characters() {
		Motto motto = Motto.of(MOTTO);

		assertThatThrownBy(() -> motto.update(FORBIDDEN_MOTTO))
			.isInstanceOf(MottoConstraintViolationException.class)
			.hasMessageContaining(MottoErrorCode.FORBIDDEN.getMessage());
	}
}
