package com.gomo.app.core.auth.domain.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("[Domain unit]: 인증 코드 생성 및 비교 테스트")
class AuthCodeTest {

	@DisplayName("AuthCode는 6자리 숫자 형식이다.")
	@Test
	void generate_6digit_numeric_authCode() {
		AuthCode authCode = AuthCode.generate();
		assertThat(authCode.getValue()).hasSize(6);
		assertThat(authCode.getValue()).matches("\\d{6}");
	}

	@DisplayName("from() 메서드는 유효하지 않은 문자열로 생성 시 예외를 발생시킨다")
	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"12345", "1234567", "abcdef", "123a56", " "})
	void from_with_invalid_string_should_throw_exception(String invalidCode) {
		assertThatThrownBy(() -> AuthCode.from(invalidCode))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Auth code value must be 6 digits");
	}

	@DisplayName("matches() 메서드는 같은 값을 가진 AuthCode에 대해 true를 반환한다")
	@Test
	void matches_with_same_value_should_return_true() {
		AuthCode code1 = AuthCode.from("999888");
		AuthCode code2 = AuthCode.from("999888");
		boolean result = code1.matches(code2);
		assertThat(result).isTrue();
	}

	@DisplayName("matches() 메서드는 다른 값을 가진 AuthCode에 대해 false를 반환한다")
	@Test
	void matches_with_different_value_should_return_false() {
		AuthCode code1 = AuthCode.from("999888");
		AuthCode code2 = AuthCode.from("111222");
		boolean result = code1.matches(code2);
		assertThat(result).isFalse();
	}
}
